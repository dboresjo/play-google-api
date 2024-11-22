package com.boresjo.play.api.google.mybusinesslodging

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Lodging(
	  /** Required. Google identifier for this location in the form: `locations/{location_id}/lodging` */
		name: Option[String] = None,
	  /** Required. Metadata for the lodging. */
		metadata: Option[Schema.LodgingMetadata] = None,
	  /** General factual information about the property's physical structure and important dates. */
		property: Option[Schema.Property] = None,
	  /** Conveniences or help provided by the property to facilitate an easier, more comfortable stay. */
		services: Option[Schema.Services] = None,
	  /** Property rules that impact guests. */
		policies: Option[Schema.Policies] = None,
	  /** Meals, snacks, and beverages available at the property. */
		foodAndDrink: Option[Schema.FoodAndDrink] = None,
	  /** Swimming pool or recreational water facilities available at the hotel. */
		pools: Option[Schema.Pools] = None,
	  /** Guest facilities at the property to promote or maintain health, beauty, and fitness. */
		wellness: Option[Schema.Wellness] = None,
	  /** Amenities and features related to leisure and play. */
		activities: Option[Schema.Activities] = None,
	  /** Vehicles or vehicular services facilitated or owned by the property. */
		transportation: Option[Schema.Transportation] = None,
	  /** Services and amenities for families and young guests. */
		families: Option[Schema.Families] = None,
	  /** The ways in which the property provides guests with the ability to access the internet. */
		connectivity: Option[Schema.Connectivity] = None,
	  /** Features of the property of specific interest to the business traveler. */
		business: Option[Schema.Business] = None,
	  /** Physical adaptations made to the property in consideration of varying levels of human physical ability. */
		accessibility: Option[Schema.Accessibility] = None,
	  /** Policies regarding guest-owned animals. */
		pets: Option[Schema.Pets] = None,
	  /** Parking options at the property. */
		parking: Option[Schema.Parking] = None,
	  /** Conveniences provided in guest units to facilitate an easier, more comfortable stay. */
		housekeeping: Option[Schema.Housekeeping] = None,
	  /** Health and safety measures implemented by the hotel during COVID-19. */
		healthAndSafety: Option[Schema.HealthAndSafety] = None,
	  /** Sustainability practices implemented at the hotel. */
		sustainability: Option[Schema.Sustainability] = None,
	  /** Features of the shared living areas available in this Lodging. */
		commonLivingArea: Option[Schema.LivingArea] = None,
	  /** Individual GuestUnitTypes that are available in this Lodging. */
		guestUnits: Option[List[Schema.GuestUnitType]] = None,
	  /** Output only. All units on the property have at least these attributes. */
		allUnits: Option[Schema.GuestUnitFeatures] = None,
	  /** Output only. Some units on the property have as much as these attributes. */
		someUnits: Option[Schema.GuestUnitFeatures] = None
	)
	
	case class LodgingMetadata(
	  /** Required. The latest time at which the Lodging data is asserted to be true in the real world. This is not necessarily the time at which the request is made. */
		updateTime: Option[String] = None
	)
	
	object Property {
		enum BuiltYearExceptionEnum extends Enum[BuiltYearExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum LastRenovatedYearExceptionEnum extends Enum[LastRenovatedYearExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum RoomsCountExceptionEnum extends Enum[RoomsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FloorsCountExceptionEnum extends Enum[FloorsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Property(
	  /** Built year. The year that construction of the property was completed. */
		builtYear: Option[Int] = None,
	  /** Built year exception. */
		builtYearException: Option[Schema.Property.BuiltYearExceptionEnum] = None,
	  /** Last renovated year. The year when the most recent renovation of the property was completed. Renovation may include all or any combination of the following: the units, the public spaces, the exterior, or the interior. */
		lastRenovatedYear: Option[Int] = None,
	  /** Last renovated year exception. */
		lastRenovatedYearException: Option[Schema.Property.LastRenovatedYearExceptionEnum] = None,
	  /** Rooms count. The total number of rooms and suites bookable by guests for an overnight stay. Does not include event space, public spaces, conference rooms, fitness rooms, business centers, spa, salon, restaurants/bars, or shops. */
		roomsCount: Option[Int] = None,
	  /** Rooms count exception. */
		roomsCountException: Option[Schema.Property.RoomsCountExceptionEnum] = None,
	  /** Floors count. The number of stories the building has from the ground floor to the top floor that are accessible to guests. */
		floorsCount: Option[Int] = None,
	  /** Floors count exception. */
		floorsCountException: Option[Schema.Property.FloorsCountExceptionEnum] = None
	)
	
	object Services {
		enum FrontDeskExceptionEnum extends Enum[FrontDeskExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TwentyFourHourFrontDeskExceptionEnum extends Enum[TwentyFourHourFrontDeskExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ConciergeExceptionEnum extends Enum[ConciergeExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ElevatorExceptionEnum extends Enum[ElevatorExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BaggageStorageExceptionEnum extends Enum[BaggageStorageExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FullServiceLaundryExceptionEnum extends Enum[FullServiceLaundryExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SelfServiceLaundryExceptionEnum extends Enum[SelfServiceLaundryExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SocialHourExceptionEnum extends Enum[SocialHourExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WakeUpCallsExceptionEnum extends Enum[WakeUpCallsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ConvenienceStoreExceptionEnum extends Enum[ConvenienceStoreExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum GiftShopExceptionEnum extends Enum[GiftShopExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CurrencyExchangeExceptionEnum extends Enum[CurrencyExchangeExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Services(
	  /** Front desk. A counter or desk in the lobby or the immediate interior of the hotel where a member of the staff greets guests and processes the information related to their stay (including check-in and check-out). May or may not be manned and open 24/7. */
		frontDesk: Option[Boolean] = None,
	  /** Front desk exception. */
		frontDeskException: Option[Schema.Services.FrontDeskExceptionEnum] = None,
	  /** 24hr front desk. Front desk is staffed 24 hours a day. */
		twentyFourHourFrontDesk: Option[Boolean] = None,
	  /** 24hr front desk exception. */
		twentyFourHourFrontDeskException: Option[Schema.Services.TwentyFourHourFrontDeskExceptionEnum] = None,
	  /** Concierge. Hotel staff member(s) responsible for facilitating an easy, comfortable stay through making reservations for meals, sourcing theater tickets, arranging tours, finding a doctor, making recommendations, and answering questions. */
		concierge: Option[Boolean] = None,
	  /** Concierge exception. */
		conciergeException: Option[Schema.Services.ConciergeExceptionEnum] = None,
	  /** Elevator. A passenger elevator that transports guests from one story to another. Also known as lift. */
		elevator: Option[Boolean] = None,
	  /** Elevator exception. */
		elevatorException: Option[Schema.Services.ElevatorExceptionEnum] = None,
	  /** Baggage storage. A provision for guests to leave their bags at the hotel when they arrive for their stay before the official check-in time. May or may not apply for guests who wish to leave their bags after check-out and before departing the locale. Also known as bag dropoff. */
		baggageStorage: Option[Boolean] = None,
	  /** Baggage storage exception. */
		baggageStorageException: Option[Schema.Services.BaggageStorageExceptionEnum] = None,
	  /** Full service laundry. Laundry and dry cleaning facilitated and handled by the hotel on behalf of the guest. Does not include the provision for guests to do their own laundry in on-site machines. */
		fullServiceLaundry: Option[Boolean] = None,
	  /** Full service laundry exception. */
		fullServiceLaundryException: Option[Schema.Services.FullServiceLaundryExceptionEnum] = None,
	  /** Self service laundry. On-site clothes washers and dryers accessible to guests for the purpose of washing and drying their own clothes. May or may not require payment to use the machines. */
		selfServiceLaundry: Option[Boolean] = None,
	  /** Self service laundry exception. */
		selfServiceLaundryException: Option[Schema.Services.SelfServiceLaundryExceptionEnum] = None,
	  /** Social hour. A reception with complimentary soft drinks, tea, coffee, wine and/or cocktails in the afternoon or evening. Can be hosted by hotel staff or guests may serve themselves. Also known as wine hour. The availability of coffee/tea in the lobby throughout the day does not constitute a social or wine hour. */
		socialHour: Option[Boolean] = None,
	  /** Social hour exception. */
		socialHourException: Option[Schema.Services.SocialHourExceptionEnum] = None,
	  /** Wake up calls. By direction of the guest, a hotel staff member will phone the guest unit at the requested hour. Also known as morning call. */
		wakeUpCalls: Option[Boolean] = None,
	  /** Wake up calls exception. */
		wakeUpCallsException: Option[Schema.Services.WakeUpCallsExceptionEnum] = None,
	  /** Convenience store. A shop at the hotel primarily selling snacks, drinks, non-prescription medicines, health and beauty aids, magazines and newspapers. */
		convenienceStore: Option[Boolean] = None,
	  /** Convenience store exception. */
		convenienceStoreException: Option[Schema.Services.ConvenienceStoreExceptionEnum] = None,
	  /** Gift shop. An on-site store primarily selling souvenirs, mementos and other gift items. May or may not also sell sundries, magazines and newspapers, clothing, or snacks. */
		giftShop: Option[Boolean] = None,
	  /** Gift shop exception. */
		giftShopException: Option[Schema.Services.GiftShopExceptionEnum] = None,
	  /** Currency exchange. A staff member or automated machine tasked with the transaction of providing the native currency of the hotel's locale in exchange for the foreign currency provided by a guest. */
		currencyExchange: Option[Boolean] = None,
	  /** Currency exchange exception. */
		currencyExchangeException: Option[Schema.Services.CurrencyExchangeExceptionEnum] = None,
	  /** Languages spoken by at least one staff member. */
		languagesSpoken: Option[List[Schema.LanguageSpoken]] = None
	)
	
	object LanguageSpoken {
		enum SpokenExceptionEnum extends Enum[SpokenExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class LanguageSpoken(
	  /** Required. The BCP-47 language code for the spoken language. Currently accepted codes: ar, de, en, es, fil, fr, hi, id, it, ja, ko, nl, pt, ru, vi, yue, zh. */
		languageCode: Option[String] = None,
	  /** At least one member of the staff can speak the language. */
		spoken: Option[Boolean] = None,
	  /** Spoken exception. */
		spokenException: Option[Schema.LanguageSpoken.SpokenExceptionEnum] = None
	)
	
	object Policies {
		enum CheckinTimeExceptionEnum extends Enum[CheckinTimeExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CheckoutTimeExceptionEnum extends Enum[CheckoutTimeExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum KidsStayFreeExceptionEnum extends Enum[KidsStayFreeExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MaxKidsStayFreeCountExceptionEnum extends Enum[MaxKidsStayFreeCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MaxChildAgeExceptionEnum extends Enum[MaxChildAgeExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SmokeFreePropertyExceptionEnum extends Enum[SmokeFreePropertyExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum AllInclusiveAvailableExceptionEnum extends Enum[AllInclusiveAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum AllInclusiveOnlyExceptionEnum extends Enum[AllInclusiveOnlyExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Policies(
	  /** Check-in time. The time of the day at which the hotel begins providing guests access to their unit at the beginning of their stay. */
		checkinTime: Option[Schema.TimeOfDay] = None,
	  /** Check-in time exception. */
		checkinTimeException: Option[Schema.Policies.CheckinTimeExceptionEnum] = None,
	  /** Check-out time. The time of the day on the last day of a guest's reserved stay at which the guest must vacate their room and settle their bill. Some hotels may offer late or early check out for a fee. */
		checkoutTime: Option[Schema.TimeOfDay] = None,
	  /** Check-out time exception. */
		checkoutTimeException: Option[Schema.Policies.CheckoutTimeExceptionEnum] = None,
	  /** Kids stay free. The children of guests are allowed to stay in the room/suite of a parent or adult without an additional fee. The policy may or may not stipulate a limit of the child's age or the overall number of children allowed. */
		kidsStayFree: Option[Boolean] = None,
	  /** Kids stay free exception. */
		kidsStayFreeException: Option[Schema.Policies.KidsStayFreeExceptionEnum] = None,
	  /** Max kids stay free count. The hotel allows a specific, defined number of children to stay in the room/suite of a parent or adult without an additional fee. */
		maxKidsStayFreeCount: Option[Int] = None,
	  /** Max kids stay free count exception. */
		maxKidsStayFreeCountException: Option[Schema.Policies.MaxKidsStayFreeCountExceptionEnum] = None,
	  /** Max child age. The hotel allows children up to a certain age to stay in the room/suite of a parent or adult without an additional fee. */
		maxChildAge: Option[Int] = None,
	  /** Max child age exception. */
		maxChildAgeException: Option[Schema.Policies.MaxChildAgeExceptionEnum] = None,
	  /** Smoke free property. Smoking is not allowed inside the building, on balconies, or in outside spaces. Hotels that offer a designated area for guests to smoke are not considered smoke-free properties. */
		smokeFreeProperty: Option[Boolean] = None,
	  /** Smoke free property exception. */
		smokeFreePropertyException: Option[Schema.Policies.SmokeFreePropertyExceptionEnum] = None,
	  /** All inclusive available. The hotel offers a rate option that includes the cost of the room, meals, activities, and other amenities that might otherwise be charged separately. */
		allInclusiveAvailable: Option[Boolean] = None,
	  /** All inclusive available exception. */
		allInclusiveAvailableException: Option[Schema.Policies.AllInclusiveAvailableExceptionEnum] = None,
	  /** All inclusive only. The only rate option offered by the hotel is a rate that includes the cost of the room, meals, activities and other amenities that might otherwise be charged separately. */
		allInclusiveOnly: Option[Boolean] = None,
	  /** All inclusive only exception. */
		allInclusiveOnlyException: Option[Schema.Policies.AllInclusiveOnlyExceptionEnum] = None,
	  /** Forms of payment accepted at the property. */
		paymentOptions: Option[Schema.PaymentOptions] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	object PaymentOptions {
		enum CashExceptionEnum extends Enum[CashExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ChequeExceptionEnum extends Enum[ChequeExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CreditCardExceptionEnum extends Enum[CreditCardExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DebitCardExceptionEnum extends Enum[DebitCardExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MobileNfcExceptionEnum extends Enum[MobileNfcExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class PaymentOptions(
	  /** Cash. The hotel accepts payment by paper/coin currency. */
		cash: Option[Boolean] = None,
	  /** Cash exception. */
		cashException: Option[Schema.PaymentOptions.CashExceptionEnum] = None,
	  /** Cheque. The hotel accepts a printed document issued by the guest's bank in the guest's name as a form of payment. */
		cheque: Option[Boolean] = None,
	  /** Cheque exception. */
		chequeException: Option[Schema.PaymentOptions.ChequeExceptionEnum] = None,
	  /** Credit card. The hotel accepts payment by a card issued by a bank or credit card company. Also known as charge card, debit card, bank card, or charge plate. */
		creditCard: Option[Boolean] = None,
	  /** Credit card exception. */
		creditCardException: Option[Schema.PaymentOptions.CreditCardExceptionEnum] = None,
	  /** Debit card. The hotel accepts a bank-issued card that immediately deducts the charged funds from the guest's bank account upon processing. */
		debitCard: Option[Boolean] = None,
	  /** Debit card exception. */
		debitCardException: Option[Schema.PaymentOptions.DebitCardExceptionEnum] = None,
	  /** Mobile nfc. The hotel has the compatible computer hardware terminal that reads and charges a payment app on the guest's smartphone without requiring the two devices to make physical contact. Also known as Apple Pay, Google Pay, Samsung Pay. */
		mobileNfc: Option[Boolean] = None,
	  /** Mobile nfc exception. */
		mobileNfcException: Option[Schema.PaymentOptions.MobileNfcExceptionEnum] = None
	)
	
	object FoodAndDrink {
		enum RoomServiceExceptionEnum extends Enum[RoomServiceExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TwentyFourHourRoomServiceExceptionEnum extends Enum[TwentyFourHourRoomServiceExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum RestaurantExceptionEnum extends Enum[RestaurantExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum RestaurantsCountExceptionEnum extends Enum[RestaurantsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TableServiceExceptionEnum extends Enum[TableServiceExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BuffetExceptionEnum extends Enum[BuffetExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DinnerBuffetExceptionEnum extends Enum[DinnerBuffetExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BreakfastBuffetExceptionEnum extends Enum[BreakfastBuffetExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BreakfastAvailableExceptionEnum extends Enum[BreakfastAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreeBreakfastExceptionEnum extends Enum[FreeBreakfastExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BarExceptionEnum extends Enum[BarExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum VendingMachineExceptionEnum extends Enum[VendingMachineExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class FoodAndDrink(
	  /** Room service. A hotel staffer delivers meals prepared onsite to a guest's room as per their request. May or may not be available during specific hours. Services should be available to all guests (not based on rate/room booked/reward program, etc). */
		roomService: Option[Boolean] = None,
	  /** Room service exception. */
		roomServiceException: Option[Schema.FoodAndDrink.RoomServiceExceptionEnum] = None,
	  /** 24hr room service. Room service is available 24 hours a day. */
		twentyFourHourRoomService: Option[Boolean] = None,
	  /** 24hr room service exception. */
		twentyFourHourRoomServiceException: Option[Schema.FoodAndDrink.TwentyFourHourRoomServiceExceptionEnum] = None,
	  /** Restaurant. A business onsite at the hotel that is open to the public as well as guests, and offers meals and beverages to consume at tables or counters. May or may not include table service. Also known as cafe, buffet, eatery. A "breakfast room" where the hotel serves breakfast only to guests (not the general public) does not count as a restaurant. */
		restaurant: Option[Boolean] = None,
	  /** Restaurant exception. */
		restaurantException: Option[Schema.FoodAndDrink.RestaurantExceptionEnum] = None,
	  /** Restaurants count. The number of restaurants at the hotel. */
		restaurantsCount: Option[Int] = None,
	  /** Restaurants count exception. */
		restaurantsCountException: Option[Schema.FoodAndDrink.RestaurantsCountExceptionEnum] = None,
	  /** Table service. A restaurant in which a staff member is assigned to a guest's table to take their order, deliver and clear away food, and deliver the bill, if applicable. Also known as sit-down restaurant. */
		tableService: Option[Boolean] = None,
	  /** Table service exception. */
		tableServiceException: Option[Schema.FoodAndDrink.TableServiceExceptionEnum] = None,
	  /** Buffet. A type of meal where guests serve themselves from a variety of dishes/foods that are put out on a table. Includes lunch and/or dinner meals. A breakfast-only buffet is not sufficient. */
		buffet: Option[Boolean] = None,
	  /** Buffet exception. */
		buffetException: Option[Schema.FoodAndDrink.BuffetExceptionEnum] = None,
	  /** Dinner buffet. Dinner meal service where guests serve themselves from a variety of dishes/foods that are put out on a table. */
		dinnerBuffet: Option[Boolean] = None,
	  /** Dinner buffet exception. */
		dinnerBuffetException: Option[Schema.FoodAndDrink.DinnerBuffetExceptionEnum] = None,
	  /** Breakfast buffet. Breakfast meal service where guests serve themselves from a variety of dishes/foods that are put out on a table. */
		breakfastBuffet: Option[Boolean] = None,
	  /** Breakfast buffet exception. */
		breakfastBuffetException: Option[Schema.FoodAndDrink.BreakfastBuffetExceptionEnum] = None,
	  /** Breakfast available. The morning meal is offered to all guests. Can be free or for a fee. */
		breakfastAvailable: Option[Boolean] = None,
	  /** Breakfast available exception. */
		breakfastAvailableException: Option[Schema.FoodAndDrink.BreakfastAvailableExceptionEnum] = None,
	  /** Free breakfast. Breakfast is offered for free to all guests. Does not apply if limited to certain room packages. */
		freeBreakfast: Option[Boolean] = None,
	  /** Free breakfast exception. */
		freeBreakfastException: Option[Schema.FoodAndDrink.FreeBreakfastExceptionEnum] = None,
	  /** Bar. A designated room, lounge or area of an on-site restaurant with seating at a counter behind which a hotel staffer takes the guest's order and provides the requested alcoholic drink. Can be indoors or outdoors. Also known as Pub. */
		bar: Option[Boolean] = None,
	  /** Bar exception. */
		barException: Option[Schema.FoodAndDrink.BarExceptionEnum] = None,
	  /** Vending machine. A glass-fronted mechanized cabinet displaying and dispensing snacks and beverages for purchase by coins, paper money and/or credit cards. */
		vendingMachine: Option[Boolean] = None,
	  /** Vending machine exception. */
		vendingMachineException: Option[Schema.FoodAndDrink.VendingMachineExceptionEnum] = None
	)
	
	object Pools {
		enum PoolExceptionEnum extends Enum[PoolExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PoolsCountExceptionEnum extends Enum[PoolsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum IndoorPoolExceptionEnum extends Enum[IndoorPoolExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum IndoorPoolsCountExceptionEnum extends Enum[IndoorPoolsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum OutdoorPoolExceptionEnum extends Enum[OutdoorPoolExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum OutdoorPoolsCountExceptionEnum extends Enum[OutdoorPoolsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum HotTubExceptionEnum extends Enum[HotTubExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WaterslideExceptionEnum extends Enum[WaterslideExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum LazyRiverExceptionEnum extends Enum[LazyRiverExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum AdultPoolExceptionEnum extends Enum[AdultPoolExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WadingPoolExceptionEnum extends Enum[WadingPoolExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WavePoolExceptionEnum extends Enum[WavePoolExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WaterParkExceptionEnum extends Enum[WaterParkExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum LifeguardExceptionEnum extends Enum[LifeguardExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Pools(
	  /** Pool. The presence of a pool, either indoors or outdoors, for guests to use for swimming and/or soaking. Use may or may not be restricted to adults and/or children. */
		pool: Option[Boolean] = None,
	  /** Pool exception. */
		poolException: Option[Schema.Pools.PoolExceptionEnum] = None,
	  /** Pools count. The sum of all pools at the hotel. */
		poolsCount: Option[Int] = None,
	  /** Pools count exception. */
		poolsCountException: Option[Schema.Pools.PoolsCountExceptionEnum] = None,
	  /** Indoor pool. A pool located inside the hotel and available for guests to use for swimming and/or soaking. Use may or may not be restricted to adults and/or children. */
		indoorPool: Option[Boolean] = None,
	  /** Indoor pool exception. */
		indoorPoolException: Option[Schema.Pools.IndoorPoolExceptionEnum] = None,
	  /** Indoor pools count. The sum of all indoor pools at the hotel. */
		indoorPoolsCount: Option[Int] = None,
	  /** Indoor pools count exception. */
		indoorPoolsCountException: Option[Schema.Pools.IndoorPoolsCountExceptionEnum] = None,
	  /** Outdoor pool. A pool located outside on the grounds of the hotel and available for guests to use for swimming, soaking or recreation. Use may or may not be restricted to adults and/or children. */
		outdoorPool: Option[Boolean] = None,
	  /** Outdoor pool exception. */
		outdoorPoolException: Option[Schema.Pools.OutdoorPoolExceptionEnum] = None,
	  /** Outdoor pools count. The sum of all outdoor pools at the hotel. */
		outdoorPoolsCount: Option[Int] = None,
	  /** Outdoor pools count exception. */
		outdoorPoolsCountException: Option[Schema.Pools.OutdoorPoolsCountExceptionEnum] = None,
	  /** Hot tub. A man-made pool containing bubbling water maintained at a higher temperature and circulated by aerating jets for the purpose of soaking, relaxation and hydrotherapy. Can be indoors or outdoors. Not used for active swimming. Also known as Jacuzzi. Hot tub must be in a common area where all guests can access it. Does not apply to room-specific hot tubs that are only accessible to guest occupying that room. */
		hotTub: Option[Boolean] = None,
	  /** Hot tub exception. */
		hotTubException: Option[Schema.Pools.HotTubExceptionEnum] = None,
	  /** Waterslide. A continuously wetted chute positioned by an indoor or outdoor pool which people slide down into the water. */
		waterslide: Option[Boolean] = None,
	  /** Waterslide exception. */
		waterslideException: Option[Schema.Pools.WaterslideExceptionEnum] = None,
	  /** Lazy river. A man-made pool or several interconnected recreational pools built to mimic the shape and current of a winding river where guests float in the water on inflated rubber tubes. Can be indoors or outdoors. */
		lazyRiver: Option[Boolean] = None,
	  /** Lazy river exception. */
		lazyRiverException: Option[Schema.Pools.LazyRiverExceptionEnum] = None,
	  /** Adult pool. A pool restricted for use by adults only. Can be indoors or outdoors. */
		adultPool: Option[Boolean] = None,
	  /** Adult pool exception. */
		adultPoolException: Option[Schema.Pools.AdultPoolExceptionEnum] = None,
	  /** Wading pool. A shallow pool designed for small children to play in. Can be indoors or outdoors. Also known as kiddie pool. */
		wadingPool: Option[Boolean] = None,
	  /** Wading pool exception. */
		wadingPoolException: Option[Schema.Pools.WadingPoolExceptionEnum] = None,
	  /** Wave pool. A large indoor or outdoor pool with a machine that produces water currents to mimic the ocean's crests. */
		wavePool: Option[Boolean] = None,
	  /** Wave pool exception. */
		wavePoolException: Option[Schema.Pools.WavePoolExceptionEnum] = None,
	  /** Water park. An aquatic recreation area with a large pool or series of pools that has features such as a water slide or tube, wavepool, fountains, rope swings, and/or obstacle course. Can be indoors or outdoors. Also known as adventure pool. */
		waterPark: Option[Boolean] = None,
	  /** Water park exception. */
		waterParkException: Option[Schema.Pools.WaterParkExceptionEnum] = None,
	  /** Lifeguard. A trained member of the hotel staff stationed by the hotel's indoor or outdoor swimming area and responsible for the safety of swimming guests. */
		lifeguard: Option[Boolean] = None,
	  /** Lifeguard exception. */
		lifeguardException: Option[Schema.Pools.LifeguardExceptionEnum] = None
	)
	
	object Wellness {
		enum FitnessCenterExceptionEnum extends Enum[FitnessCenterExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreeFitnessCenterExceptionEnum extends Enum[FreeFitnessCenterExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum EllipticalMachineExceptionEnum extends Enum[EllipticalMachineExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TreadmillExceptionEnum extends Enum[TreadmillExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WeightMachineExceptionEnum extends Enum[WeightMachineExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreeWeightsExceptionEnum extends Enum[FreeWeightsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SpaExceptionEnum extends Enum[SpaExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SalonExceptionEnum extends Enum[SalonExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SaunaExceptionEnum extends Enum[SaunaExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MassageExceptionEnum extends Enum[MassageExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DoctorOnCallExceptionEnum extends Enum[DoctorOnCallExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Wellness(
	  /** Fitness center. A room or building at the hotel containing equipment to promote physical activity, such as treadmills, elliptical machines, stationary bikes, weight machines, free weights, and/or stretching mats. Use of the fitness center can be free or for a fee. May or may not be staffed. May or may not offer instructor-led classes in various styles of physical conditioning. May or may not be open 24/7. May or may not include locker rooms and showers. Also known as health club, gym, fitness room, health center. */
		fitnessCenter: Option[Boolean] = None,
	  /** Fitness center exception. */
		fitnessCenterException: Option[Schema.Wellness.FitnessCenterExceptionEnum] = None,
	  /** Free fitness center. Guests may use the fitness center for free. */
		freeFitnessCenter: Option[Boolean] = None,
	  /** Free fitness center exception. */
		freeFitnessCenterException: Option[Schema.Wellness.FreeFitnessCenterExceptionEnum] = None,
	  /** Elliptical machine. An electric, stationary fitness machine with pedals that simulates climbing, walking or running and provides a user-controlled range of speeds and tensions. May not have arm-controlled levers to work out the upper body as well. Commonly found in a gym, fitness room, health center, or health club. */
		ellipticalMachine: Option[Boolean] = None,
	  /** Elliptical machine exception. */
		ellipticalMachineException: Option[Schema.Wellness.EllipticalMachineExceptionEnum] = None,
	  /** Treadmill. An electric stationary fitness machine that simulates a moving path to promote walking or running within a range of user-controlled speeds and inclines. Also known as running machine. Commonly found in a gym, fitness room, health center, or health club. */
		treadmill: Option[Boolean] = None,
	  /** Treadmill exception. */
		treadmillException: Option[Schema.Wellness.TreadmillExceptionEnum] = None,
	  /** Weight machine. Non-electronic fitness equipment designed for the user to target the exertion of different muscles. Usually incorporates a padded seat, a stack of flat weights and various bars and pulleys. May be designed for toning a specific part of the body or may involve different user-controlled settings, hardware and pulleys so as to provide an overall workout in one machine. Commonly found in a gym, fitness center, fitness room, or health club. */
		weightMachine: Option[Boolean] = None,
	  /** Weight machine exception. */
		weightMachineException: Option[Schema.Wellness.WeightMachineExceptionEnum] = None,
	  /** Free weights. Individual handheld fitness equipment of varied weights used for upper body strength training or bodybuilding. Also known as barbells, dumbbells, or kettlebells. Often stored on a rack with the weights arranged from light to heavy. Commonly found in a gym, fitness room, health center, or health club. */
		freeWeights: Option[Boolean] = None,
	  /** Free weights exception. */
		freeWeightsException: Option[Schema.Wellness.FreeWeightsExceptionEnum] = None,
	  /** Spa. A designated area, room or building at the hotel offering health and beauty treatment through such means as steam baths, exercise equipment, and massage. May also offer facials, nail care, and hair care. Services are usually available by appointment and for an additional fee. Does not apply if hotel only offers a steam room; must offer other beauty and/or health treatments as well. */
		spa: Option[Boolean] = None,
	  /** Spa exception. */
		spaException: Option[Schema.Wellness.SpaExceptionEnum] = None,
	  /** Salon. A room at the hotel where professionals provide hair styling services such as shampooing, blow drying, hair dos, hair cutting and hair coloring. Also known as hairdresser or beauty salon. */
		salon: Option[Boolean] = None,
	  /** Salon exception. */
		salonException: Option[Schema.Wellness.SalonExceptionEnum] = None,
	  /** Sauna. A wood-paneled room heated to a high temperature where guests sit on built-in wood benches for the purpose of perspiring and relaxing their muscles. Can be dry or slightly wet heat. Not a steam room. */
		sauna: Option[Boolean] = None,
	  /** Sauna exception. */
		saunaException: Option[Schema.Wellness.SaunaExceptionEnum] = None,
	  /** Massage. A service provided by a trained massage therapist involving the physical manipulation of a guest's muscles in order to achieve relaxation or pain relief. */
		massage: Option[Boolean] = None,
	  /** Massage exception. */
		massageException: Option[Schema.Wellness.MassageExceptionEnum] = None,
	  /** Doctor on call. The hotel has a contract with a medical professional who provides services to hotel guests should they fall ill during their stay. The doctor may or may not have an on-site office or be at the hotel at all times. */
		doctorOnCall: Option[Boolean] = None,
	  /** Doctor on call exception. */
		doctorOnCallException: Option[Schema.Wellness.DoctorOnCallExceptionEnum] = None
	)
	
	object Activities {
		enum GameRoomExceptionEnum extends Enum[GameRoomExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum NightclubExceptionEnum extends Enum[NightclubExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CasinoExceptionEnum extends Enum[CasinoExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BoutiqueStoresExceptionEnum extends Enum[BoutiqueStoresExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TennisExceptionEnum extends Enum[TennisExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum GolfExceptionEnum extends Enum[GolfExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum HorsebackRidingExceptionEnum extends Enum[HorsebackRidingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SnorkelingExceptionEnum extends Enum[SnorkelingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ScubaExceptionEnum extends Enum[ScubaExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WaterSkiingExceptionEnum extends Enum[WaterSkiingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BicycleRentalExceptionEnum extends Enum[BicycleRentalExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreeBicycleRentalExceptionEnum extends Enum[FreeBicycleRentalExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WatercraftRentalExceptionEnum extends Enum[WatercraftRentalExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreeWatercraftRentalExceptionEnum extends Enum[FreeWatercraftRentalExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BeachAccessExceptionEnum extends Enum[BeachAccessExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PrivateBeachExceptionEnum extends Enum[PrivateBeachExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BeachFrontExceptionEnum extends Enum[BeachFrontExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Activities(
	  /** Game room. There is a room at the hotel containing electronic machines for play such as pinball, prize machines, driving simulators, and other items commonly found at a family fun center or arcade. May also include non-electronic games like pool, foosball, darts, and more. May or may not be designed for children. Also known as arcade, fun room, or family fun center. */
		gameRoom: Option[Boolean] = None,
	  /** Game room exception. */
		gameRoomException: Option[Schema.Activities.GameRoomExceptionEnum] = None,
	  /** Nightclub. There is a room at the hotel with a bar, a dance floor, and seating where designated staffers play dance music. There may also be a designated area for the performance of live music, singing and comedy acts. */
		nightclub: Option[Boolean] = None,
	  /** Nightclub exception. */
		nightclubException: Option[Schema.Activities.NightclubExceptionEnum] = None,
	  /** Casino. A space designated for gambling and gaming featuring croupier-run table and card games, as well as electronic slot machines. May be on hotel premises or located nearby. */
		casino: Option[Boolean] = None,
	  /** Casino exception. */
		casinoException: Option[Schema.Activities.CasinoExceptionEnum] = None,
	  /** Boutique stores. There are stores selling clothing, jewelry, art and decor either on hotel premises or very close by. Does not refer to the hotel gift shop or convenience store. */
		boutiqueStores: Option[Boolean] = None,
	  /** Boutique stores exception. */
		boutiqueStoresException: Option[Schema.Activities.BoutiqueStoresExceptionEnum] = None,
	  /** Tennis. The hotel has the requisite court(s) on site or has an affiliation with a nearby facility for the purpose of providing guests with the opportunity to play a two-sided court-based game in which players use a stringed racquet to hit a ball across a net to the side of the opposing player. The court can be indoors or outdoors. Instructors, racquets and balls may or may not be provided. */
		tennis: Option[Boolean] = None,
	  /** Tennis exception. */
		tennisException: Option[Schema.Activities.TennisExceptionEnum] = None,
	  /** Golf. There is a golf course on hotel grounds or there is a nearby, independently run golf course that allows use by hotel guests. Can be free or for a fee. */
		golf: Option[Boolean] = None,
	  /** Golf exception. */
		golfException: Option[Schema.Activities.GolfExceptionEnum] = None,
	  /** Horseback riding. The hotel has a horse barn onsite or an affiliation with a nearby barn to allow for guests to sit astride a horse and direct it to walk, trot, cantor, gallop and/or jump. Can be in a riding ring, on designated paths, or in the wilderness. May or may not involve instruction. */
		horsebackRiding: Option[Boolean] = None,
	  /** Horseback riding exception. */
		horsebackRidingException: Option[Schema.Activities.HorsebackRidingExceptionEnum] = None,
	  /** Snorkeling. The provision for guests to participate in a recreational water activity in which swimmers wear a diving mask, a simple, shaped breathing tube and flippers/swim fins for the purpose of exploring below the surface of an ocean, gulf or lake. Does not usually require user certification or professional supervision. Equipment may or may not be available for rent or purchase. Not scuba diving. */
		snorkeling: Option[Boolean] = None,
	  /** Snorkeling exception. */
		snorkelingException: Option[Schema.Activities.SnorkelingExceptionEnum] = None,
	  /** Scuba. The provision for guests to dive under naturally occurring water fitted with a self-contained underwater breathing apparatus (SCUBA) for the purpose of exploring underwater life. Apparatus consists of a tank providing oxygen to the diver through a mask. Requires certification of the diver and supervision. The hotel may have the activity at its own waterfront or have an affiliation with a nearby facility. Required equipment is most often supplied to guests. Can be free or for a fee. Not snorkeling. Not done in a swimming pool. */
		scuba: Option[Boolean] = None,
	  /** Scuba exception. */
		scubaException: Option[Schema.Activities.ScubaExceptionEnum] = None,
	  /** Water skiing. The provision of giving guests the opportunity to be pulled across naturally occurring water while standing on skis and holding a tow rope attached to a motorboat. Can occur on hotel premises or at a nearby waterfront. Most often performed in a lake or ocean. */
		waterSkiing: Option[Boolean] = None,
	  /** Water skiing exception. */
		waterSkiingException: Option[Schema.Activities.WaterSkiingExceptionEnum] = None,
	  /** Bicycle rental. The hotel owns bicycles that it permits guests to borrow and use. Can be free or for a fee. */
		bicycleRental: Option[Boolean] = None,
	  /** Bicycle rental exception. */
		bicycleRentalException: Option[Schema.Activities.BicycleRentalExceptionEnum] = None,
	  /** Free bicycle rental. The hotel owns bicycles that it permits guests to borrow and use for free. */
		freeBicycleRental: Option[Boolean] = None,
	  /** Free bicycle rental exception. */
		freeBicycleRentalException: Option[Schema.Activities.FreeBicycleRentalExceptionEnum] = None,
	  /** Watercraft rental. The hotel owns water vessels that it permits guests to borrow and use. Can be free or for a fee. Watercraft may include boats, pedal boats, rowboats, sailboats, powerboats, canoes, kayaks, or personal watercraft (such as a Jet Ski). */
		watercraftRental: Option[Boolean] = None,
	  /** Watercraft rental exception. */
		watercraftRentalException: Option[Schema.Activities.WatercraftRentalExceptionEnum] = None,
	  /** Free watercraft rental. The hotel owns watercraft that it permits guests to borrow and use for free. */
		freeWatercraftRental: Option[Boolean] = None,
	  /** Free Watercraft rental exception. */
		freeWatercraftRentalException: Option[Schema.Activities.FreeWatercraftRentalExceptionEnum] = None,
	  /** Beach access. The hotel property is in close proximity to a beach and offers a way to get to that beach. This can include a route to the beach such as stairs down if hotel is on a bluff, or a short trail. Not the same as beachfront (with beach access, the hotel's proximity is close to but not right on the beach). */
		beachAccess: Option[Boolean] = None,
	  /** Beach access exception. */
		beachAccessException: Option[Schema.Activities.BeachAccessExceptionEnum] = None,
	  /** Private beach. The beach which is in close proximity to the hotel is open only to guests. */
		privateBeach: Option[Boolean] = None,
	  /** Private beach exception. */
		privateBeachException: Option[Schema.Activities.PrivateBeachExceptionEnum] = None,
	  /** Breach front. The hotel property is physically located on the beach alongside an ocean, sea, gulf, or bay. It is not on a lake, river, stream, or pond. The hotel is not separated from the beach by a public road allowing vehicular, pedestrian, or bicycle traffic. */
		beachFront: Option[Boolean] = None,
	  /** Beach front exception. */
		beachFrontException: Option[Schema.Activities.BeachFrontExceptionEnum] = None
	)
	
	object Transportation {
		enum TransferExceptionEnum extends Enum[TransferExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum AirportShuttleExceptionEnum extends Enum[AirportShuttleExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreeAirportShuttleExceptionEnum extends Enum[FreeAirportShuttleExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum LocalShuttleExceptionEnum extends Enum[LocalShuttleExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CarRentalOnPropertyExceptionEnum extends Enum[CarRentalOnPropertyExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PrivateCarServiceExceptionEnum extends Enum[PrivateCarServiceExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreePrivateCarServiceExceptionEnum extends Enum[FreePrivateCarServiceExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Transportation(
	  /** Transfer. Hotel provides a shuttle service or car service to take guests to and from the nearest airport or train station. Can be free or for a fee. Guests may share the vehicle with other guests unknown to them. */
		transfer: Option[Boolean] = None,
	  /** Transfer exception. */
		transferException: Option[Schema.Transportation.TransferExceptionEnum] = None,
	  /** Airport shuttle. The hotel provides guests with a chauffeured van or bus to and from the airport. Can be free or for a fee. Guests may share the vehicle with other guests unknown to them. Applies if the hotel has a third-party shuttle service (office/desk etc.) within the hotel. As long as hotel provides this service, it doesn't matter if it's directly with them or a third party they work with. Does not apply if guest has to coordinate with an entity outside/other than the hotel. */
		airportShuttle: Option[Boolean] = None,
	  /** Airport shuttle exception. */
		airportShuttleException: Option[Schema.Transportation.AirportShuttleExceptionEnum] = None,
	  /** Free airport shuttle. Airport shuttle is free to guests. Must be free to all guests without any conditions. */
		freeAirportShuttle: Option[Boolean] = None,
	  /** Free airport shuttle exception. */
		freeAirportShuttleException: Option[Schema.Transportation.FreeAirportShuttleExceptionEnum] = None,
	  /** Local shuttle. A car, van or bus provided by the hotel to transport guests to destinations within a specified range of distance around the hotel. Usually shopping and/or convention centers, downtown districts, or beaches. Can be free or for a fee. */
		localShuttle: Option[Boolean] = None,
	  /** Local shuttle exception. */
		localShuttleException: Option[Schema.Transportation.LocalShuttleExceptionEnum] = None,
	  /** Car rental on property. A branch of a rental car company with a processing desk in the hotel. Available cars for rent may be awaiting at the hotel or in a nearby lot. */
		carRentalOnProperty: Option[Boolean] = None,
	  /** Car rental on property exception. */
		carRentalOnPropertyException: Option[Schema.Transportation.CarRentalOnPropertyExceptionEnum] = None,
	  /** Private car service. Hotel provides a private chauffeured car to transport guests to destinations. Passengers in the car are either alone or are known to one another and have requested the car together. Service can be free or for a fee and travel distance is usually limited to a specific range. Not a taxi. */
		privateCarService: Option[Boolean] = None,
	  /** Private car service exception. */
		privateCarServiceException: Option[Schema.Transportation.PrivateCarServiceExceptionEnum] = None,
	  /** Free private car service. Private chauffeured car service is free to guests. */
		freePrivateCarService: Option[Boolean] = None,
	  /** Free private car service exception. */
		freePrivateCarServiceException: Option[Schema.Transportation.FreePrivateCarServiceExceptionEnum] = None
	)
	
	object Families {
		enum BabysittingExceptionEnum extends Enum[BabysittingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum KidsActivitiesExceptionEnum extends Enum[KidsActivitiesExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum KidsClubExceptionEnum extends Enum[KidsClubExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum KidsFriendlyExceptionEnum extends Enum[KidsFriendlyExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Families(
	  /** Babysitting. Child care that is offered by hotel staffers or coordinated by hotel staffers with local child care professionals. Can be free or for a fee. */
		babysitting: Option[Boolean] = None,
	  /** Babysitting exception. */
		babysittingException: Option[Schema.Families.BabysittingExceptionEnum] = None,
	  /** Kids activities. Recreational options such as sports, films, crafts and games designed for the enjoyment of children and offered at the hotel. May or may not be supervised. May or may not be at a designated time or place. Cab be free or for a fee. */
		kidsActivities: Option[Boolean] = None,
	  /** Kids activities exception. */
		kidsActivitiesException: Option[Schema.Families.KidsActivitiesExceptionEnum] = None,
	  /** Kids club. An organized program of group activities held at the hotel and designed for the enjoyment of children. Facilitated by hotel staff (or staff procured by the hotel) in an area(s) designated for the purpose of entertaining children without their parents. May include games, outings, water sports, team sports, arts and crafts, and films. Usually has set hours. Can be free or for a fee. Also known as Kids Camp or Kids program. */
		kidsClub: Option[Boolean] = None,
	  /** Kids club exception. */
		kidsClubException: Option[Schema.Families.KidsClubExceptionEnum] = None,
	  /** Kids friendly. The hotel has one or more special features for families with children, such as reduced rates, child-sized beds, kids' club, babysitting service, or suitable place to play on premises. */
		kidsFriendly: Option[Boolean] = None,
	  /** Kids friendly exception. */
		kidsFriendlyException: Option[Schema.Families.KidsFriendlyExceptionEnum] = None
	)
	
	object Connectivity {
		enum WifiAvailableExceptionEnum extends Enum[WifiAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreeWifiExceptionEnum extends Enum[FreeWifiExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PublicAreaWifiAvailableExceptionEnum extends Enum[PublicAreaWifiAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PublicInternetTerminalExceptionEnum extends Enum[PublicInternetTerminalExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Connectivity(
	  /** Wifi available. The hotel provides the ability for guests to wirelessly connect to the internet. Can be in the public areas of the hotel and/or in the guest rooms. Can be free or for a fee. */
		wifiAvailable: Option[Boolean] = None,
	  /** Wifi available exception. */
		wifiAvailableException: Option[Schema.Connectivity.WifiAvailableExceptionEnum] = None,
	  /** Free wifi. The hotel offers guests wifi for free. */
		freeWifi: Option[Boolean] = None,
	  /** Free wifi exception. */
		freeWifiException: Option[Schema.Connectivity.FreeWifiExceptionEnum] = None,
	  /** Public area wifi available. Guests have the ability to wirelessly connect to the internet in the areas of the hotel accessible to anyone. Can be free or for a fee. */
		publicAreaWifiAvailable: Option[Boolean] = None,
	  /** Public area wifi available exception. */
		publicAreaWifiAvailableException: Option[Schema.Connectivity.PublicAreaWifiAvailableExceptionEnum] = None,
	  /** Public internet terminal. An area of the hotel supplied with computers and designated for the purpose of providing guests with the ability to access the internet. */
		publicInternetTerminal: Option[Boolean] = None,
	  /** Public internet terminal exception. */
		publicInternetTerminalException: Option[Schema.Connectivity.PublicInternetTerminalExceptionEnum] = None
	)
	
	object Business {
		enum BusinessCenterExceptionEnum extends Enum[BusinessCenterExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MeetingRoomsExceptionEnum extends Enum[MeetingRoomsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MeetingRoomsCountExceptionEnum extends Enum[MeetingRoomsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Business(
	  /** Business center. A designated room at the hotel with one or more desks and equipped with guest-use computers, printers, fax machines and/or photocopiers. May or may not be open 24/7. May or may not require a key to access. Not a meeting room or conference room. */
		businessCenter: Option[Boolean] = None,
	  /** Business center exception. */
		businessCenterException: Option[Schema.Business.BusinessCenterExceptionEnum] = None,
	  /** Meeting rooms. Rooms at the hotel designated for business-related gatherings. Rooms are usually equipped with tables or desks, office chairs and audio/visual facilities to allow for presentations and conference calls. Also known as conference rooms. */
		meetingRooms: Option[Boolean] = None,
	  /** Meeting rooms exception. */
		meetingRoomsException: Option[Schema.Business.MeetingRoomsExceptionEnum] = None,
	  /** Meeting rooms count. The number of meeting rooms at the property. */
		meetingRoomsCount: Option[Int] = None,
	  /** Meeting rooms count exception. */
		meetingRoomsCountException: Option[Schema.Business.MeetingRoomsCountExceptionEnum] = None
	)
	
	object Accessibility {
		enum MobilityAccessibleExceptionEnum extends Enum[MobilityAccessibleExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MobilityAccessibleParkingExceptionEnum extends Enum[MobilityAccessibleParkingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MobilityAccessibleElevatorExceptionEnum extends Enum[MobilityAccessibleElevatorExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MobilityAccessiblePoolExceptionEnum extends Enum[MobilityAccessiblePoolExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Accessibility(
	  /** Mobility accessible. Throughout the property there are physical adaptations to ease the stay of a person in a wheelchair, such as auto-opening doors, wide elevators, wide bathrooms or ramps. */
		mobilityAccessible: Option[Boolean] = None,
	  /** Mobility accessible exception. */
		mobilityAccessibleException: Option[Schema.Accessibility.MobilityAccessibleExceptionEnum] = None,
	  /** Mobility accessible parking. The presence of a marked, designated area of prescribed size in which only registered, labeled vehicles transporting a person with physical challenges may park. */
		mobilityAccessibleParking: Option[Boolean] = None,
	  /** Mobility accessible parking exception. */
		mobilityAccessibleParkingException: Option[Schema.Accessibility.MobilityAccessibleParkingExceptionEnum] = None,
	  /** Mobility accessible elevator. A lift that transports people from one level to another and is built to accommodate a wheelchair-using passenger owing to the width of its doors and placement of call buttons. */
		mobilityAccessibleElevator: Option[Boolean] = None,
	  /** Mobility accessible elevator exception. */
		mobilityAccessibleElevatorException: Option[Schema.Accessibility.MobilityAccessibleElevatorExceptionEnum] = None,
	  /** Mobility accessible pool. A swimming pool equipped with a mechanical chair that can be lowered and raised for the purpose of moving physically challenged guests into and out of the pool. May be powered by electricity or water. Also known as pool lift. */
		mobilityAccessiblePool: Option[Boolean] = None,
	  /** Mobility accessible pool exception. */
		mobilityAccessiblePoolException: Option[Schema.Accessibility.MobilityAccessiblePoolExceptionEnum] = None
	)
	
	object Pets {
		enum PetsAllowedExceptionEnum extends Enum[PetsAllowedExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PetsAllowedFreeExceptionEnum extends Enum[PetsAllowedFreeExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DogsAllowedExceptionEnum extends Enum[DogsAllowedExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CatsAllowedExceptionEnum extends Enum[CatsAllowedExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Pets(
	  /** Pets allowed. Household animals are allowed at the property and in the specific guest room of their owner. May or may not include dogs, cats, reptiles and/or fish. May or may not require a fee. Service animals are not considered to be pets, so not governed by this policy. */
		petsAllowed: Option[Boolean] = None,
	  /** Pets allowed exception. */
		petsAllowedException: Option[Schema.Pets.PetsAllowedExceptionEnum] = None,
	  /** Pets allowed free. Household animals are allowed at the property and in the specific guest room of their owner for free. May or may not include dogs, cats, reptiles, and/or fish. */
		petsAllowedFree: Option[Boolean] = None,
	  /** Pets allowed free exception. */
		petsAllowedFreeException: Option[Schema.Pets.PetsAllowedFreeExceptionEnum] = None,
	  /** Dogs allowed. Domesticated canines are permitted at the property and allowed to stay in the guest room of their owner. May or may not require a fee. */
		dogsAllowed: Option[Boolean] = None,
	  /** Dogs allowed exception. */
		dogsAllowedException: Option[Schema.Pets.DogsAllowedExceptionEnum] = None,
	  /** Cats allowed. Domesticated felines are permitted at the property and allowed to stay in the guest room of their owner. May or may not require a fee. */
		catsAllowed: Option[Boolean] = None,
	  /** Cats allowed exception. */
		catsAllowedException: Option[Schema.Pets.CatsAllowedExceptionEnum] = None
	)
	
	object Parking {
		enum ParkingAvailableExceptionEnum extends Enum[ParkingAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreeParkingExceptionEnum extends Enum[FreeParkingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SelfParkingAvailableExceptionEnum extends Enum[SelfParkingAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreeSelfParkingExceptionEnum extends Enum[FreeSelfParkingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ValetParkingAvailableExceptionEnum extends Enum[ValetParkingAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FreeValetParkingExceptionEnum extends Enum[FreeValetParkingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ElectricCarChargingStationsExceptionEnum extends Enum[ElectricCarChargingStationsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Parking(
	  /** Parking available. The hotel allows the cars of guests to be parked. Can be free or for a fee. Parking facility may be an outdoor lot or an indoor garage, but must be onsite. Nearby parking does not apply. Parking may be performed by the guest or by hotel staff. */
		parkingAvailable: Option[Boolean] = None,
	  /** Parking available exception. */
		parkingAvailableException: Option[Schema.Parking.ParkingAvailableExceptionEnum] = None,
	  /** Free parking. The hotel allows the cars of guests to be parked for free. Parking facility may be an outdoor lot or an indoor garage, but must be onsite. Nearby parking does not apply. Parking may be performed by the guest or by hotel staff. Free parking must be available to all guests (limited conditions does not apply). */
		freeParking: Option[Boolean] = None,
	  /** Free parking exception. */
		freeParkingException: Option[Schema.Parking.FreeParkingExceptionEnum] = None,
	  /** Self parking available. Guests park their own cars. Parking facility may be an outdoor lot or an indoor garage, but must be onsite. Nearby parking does not apply. Can be free or for a fee. */
		selfParkingAvailable: Option[Boolean] = None,
	  /** Self parking available exception. */
		selfParkingAvailableException: Option[Schema.Parking.SelfParkingAvailableExceptionEnum] = None,
	  /** Free self parking. Guests park their own cars for free. Parking facility may be an outdoor lot or an indoor garage, but must be onsite. Nearby parking does not apply. */
		freeSelfParking: Option[Boolean] = None,
	  /** Free self parking exception. */
		freeSelfParkingException: Option[Schema.Parking.FreeSelfParkingExceptionEnum] = None,
	  /** Valet parking available. Hotel staff member parks the cars of guests. Parking with this service can be free or for a fee. */
		valetParkingAvailable: Option[Boolean] = None,
	  /** Valet parking available exception. */
		valetParkingAvailableException: Option[Schema.Parking.ValetParkingAvailableExceptionEnum] = None,
	  /** Free valet parking. Hotel staff member parks the cars of guests. Parking with this service is free. */
		freeValetParking: Option[Boolean] = None,
	  /** Free valet parking exception. */
		freeValetParkingException: Option[Schema.Parking.FreeValetParkingExceptionEnum] = None,
	  /** Electric car charging stations. Electric power stations, usually located outdoors, into which guests plug their electric cars to receive a charge. */
		electricCarChargingStations: Option[Boolean] = None,
	  /** Electric car charging stations exception. */
		electricCarChargingStationsException: Option[Schema.Parking.ElectricCarChargingStationsExceptionEnum] = None
	)
	
	object Housekeeping {
		enum HousekeepingAvailableExceptionEnum extends Enum[HousekeepingAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DailyHousekeepingExceptionEnum extends Enum[DailyHousekeepingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TurndownServiceExceptionEnum extends Enum[TurndownServiceExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class Housekeeping(
	  /** Housekeeping available. Guest units are cleaned by hotel staff during guest's stay. Schedule may vary from daily, weekly, or specific days of the week. */
		housekeepingAvailable: Option[Boolean] = None,
	  /** Housekeeping available exception. */
		housekeepingAvailableException: Option[Schema.Housekeeping.HousekeepingAvailableExceptionEnum] = None,
	  /** Daily housekeeping. Guest units are cleaned by hotel staff daily during guest's stay. */
		dailyHousekeeping: Option[Boolean] = None,
	  /** Daily housekeeping exception. */
		dailyHousekeepingException: Option[Schema.Housekeeping.DailyHousekeepingExceptionEnum] = None,
	  /** Turndown service. Hotel staff enters guest units to prepare the bed for sleep use. May or may not include some light housekeeping. May or may not include an evening snack or candy. Also known as evening service. */
		turndownService: Option[Boolean] = None,
	  /** Turndown service exception. */
		turndownServiceException: Option[Schema.Housekeeping.TurndownServiceExceptionEnum] = None
	)
	
	case class HealthAndSafety(
	  /** Enhanced cleaning measures implemented by the hotel during COVID-19. */
		enhancedCleaning: Option[Schema.EnhancedCleaning] = None,
	  /** Increased food safety measures implemented by the hotel during COVID-19. */
		increasedFoodSafety: Option[Schema.IncreasedFoodSafety] = None,
	  /** Minimized contact measures implemented by the hotel during COVID-19. */
		minimizedContact: Option[Schema.MinimizedContact] = None,
	  /** Personal protection measures implemented by the hotel during COVID-19. */
		personalProtection: Option[Schema.PersonalProtection] = None,
	  /** Physical distancing measures implemented by the hotel during COVID-19. */
		physicalDistancing: Option[Schema.PhysicalDistancing] = None
	)
	
	object EnhancedCleaning {
		enum CommonAreasEnhancedCleaningExceptionEnum extends Enum[CommonAreasEnhancedCleaningExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum GuestRoomsEnhancedCleaningExceptionEnum extends Enum[GuestRoomsEnhancedCleaningExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CommercialGradeDisinfectantCleaningExceptionEnum extends Enum[CommercialGradeDisinfectantCleaningExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum EmployeesTrainedCleaningProceduresExceptionEnum extends Enum[EmployeesTrainedCleaningProceduresExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum EmployeesTrainedThoroughHandWashingExceptionEnum extends Enum[EmployeesTrainedThoroughHandWashingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum EmployeesWearProtectiveEquipmentExceptionEnum extends Enum[EmployeesWearProtectiveEquipmentExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class EnhancedCleaning(
	  /** Enhanced cleaning of common areas. */
		commonAreasEnhancedCleaning: Option[Boolean] = None,
	  /** Common areas enhanced cleaning exception. */
		commonAreasEnhancedCleaningException: Option[Schema.EnhancedCleaning.CommonAreasEnhancedCleaningExceptionEnum] = None,
	  /** Enhanced cleaning of guest rooms. */
		guestRoomsEnhancedCleaning: Option[Boolean] = None,
	  /** Guest rooms enhanced cleaning exception. */
		guestRoomsEnhancedCleaningException: Option[Schema.EnhancedCleaning.GuestRoomsEnhancedCleaningExceptionEnum] = None,
	  /** Commercial-grade disinfectant used to clean the property. */
		commercialGradeDisinfectantCleaning: Option[Boolean] = None,
	  /** Commercial grade disinfectant cleaning exception. */
		commercialGradeDisinfectantCleaningException: Option[Schema.EnhancedCleaning.CommercialGradeDisinfectantCleaningExceptionEnum] = None,
	  /** Employees trained in COVID-19 cleaning procedures. */
		employeesTrainedCleaningProcedures: Option[Boolean] = None,
	  /** Employees trained cleaning procedures exception. */
		employeesTrainedCleaningProceduresException: Option[Schema.EnhancedCleaning.EmployeesTrainedCleaningProceduresExceptionEnum] = None,
	  /** Employees trained in thorough hand-washing. */
		employeesTrainedThoroughHandWashing: Option[Boolean] = None,
	  /** Employees trained thorough hand washing exception. */
		employeesTrainedThoroughHandWashingException: Option[Schema.EnhancedCleaning.EmployeesTrainedThoroughHandWashingExceptionEnum] = None,
	  /** Employees wear masks, face shields, and/or gloves. */
		employeesWearProtectiveEquipment: Option[Boolean] = None,
	  /** Employees wear protective equipment exception. */
		employeesWearProtectiveEquipmentException: Option[Schema.EnhancedCleaning.EmployeesWearProtectiveEquipmentExceptionEnum] = None
	)
	
	object IncreasedFoodSafety {
		enum FoodPreparationAndServingAdditionalSafetyExceptionEnum extends Enum[FoodPreparationAndServingAdditionalSafetyExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DiningAreasAdditionalSanitationExceptionEnum extends Enum[DiningAreasAdditionalSanitationExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum IndividualPackagedMealsExceptionEnum extends Enum[IndividualPackagedMealsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DisposableFlatwareExceptionEnum extends Enum[DisposableFlatwareExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SingleUseFoodMenusExceptionEnum extends Enum[SingleUseFoodMenusExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class IncreasedFoodSafety(
	  /** Additional safety measures during food prep and serving. */
		foodPreparationAndServingAdditionalSafety: Option[Boolean] = None,
	  /** Food preparation and serving additional safety exception. */
		foodPreparationAndServingAdditionalSafetyException: Option[Schema.IncreasedFoodSafety.FoodPreparationAndServingAdditionalSafetyExceptionEnum] = None,
	  /** Additional sanitation in dining areas. */
		diningAreasAdditionalSanitation: Option[Boolean] = None,
	  /** Dining areas additional sanitation exception. */
		diningAreasAdditionalSanitationException: Option[Schema.IncreasedFoodSafety.DiningAreasAdditionalSanitationExceptionEnum] = None,
	  /** Individually-packaged meals. */
		individualPackagedMeals: Option[Boolean] = None,
	  /** Individual packaged meals exception. */
		individualPackagedMealsException: Option[Schema.IncreasedFoodSafety.IndividualPackagedMealsExceptionEnum] = None,
	  /** Disposable flatware. */
		disposableFlatware: Option[Boolean] = None,
	  /** Disposable flatware exception. */
		disposableFlatwareException: Option[Schema.IncreasedFoodSafety.DisposableFlatwareExceptionEnum] = None,
	  /** Single-use menus. */
		singleUseFoodMenus: Option[Boolean] = None,
	  /** Single use food menus exception. */
		singleUseFoodMenusException: Option[Schema.IncreasedFoodSafety.SingleUseFoodMenusExceptionEnum] = None
	)
	
	object MinimizedContact {
		enum NoHighTouchItemsCommonAreasExceptionEnum extends Enum[NoHighTouchItemsCommonAreasExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum NoHighTouchItemsGuestRoomsExceptionEnum extends Enum[NoHighTouchItemsGuestRoomsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DigitalGuestRoomKeysExceptionEnum extends Enum[DigitalGuestRoomKeysExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PlasticKeycardsDisinfectedExceptionEnum extends Enum[PlasticKeycardsDisinfectedExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum RoomBookingsBufferExceptionEnum extends Enum[RoomBookingsBufferExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum HousekeepingScheduledRequestOnlyExceptionEnum extends Enum[HousekeepingScheduledRequestOnlyExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ContactlessCheckinCheckoutExceptionEnum extends Enum[ContactlessCheckinCheckoutExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class MinimizedContact(
	  /** High-touch items, such as magazines, removed from common areas. */
		noHighTouchItemsCommonAreas: Option[Boolean] = None,
	  /** No high touch items common areas exception. */
		noHighTouchItemsCommonAreasException: Option[Schema.MinimizedContact.NoHighTouchItemsCommonAreasExceptionEnum] = None,
	  /** High-touch items, such as decorative pillows, removed from guest rooms. */
		noHighTouchItemsGuestRooms: Option[Boolean] = None,
	  /** No high touch items guest rooms exception. */
		noHighTouchItemsGuestRoomsException: Option[Schema.MinimizedContact.NoHighTouchItemsGuestRoomsExceptionEnum] = None,
	  /** Keyless mobile entry to guest rooms. */
		digitalGuestRoomKeys: Option[Boolean] = None,
	  /** Digital guest room keys exception. */
		digitalGuestRoomKeysException: Option[Schema.MinimizedContact.DigitalGuestRoomKeysExceptionEnum] = None,
	  /** Plastic key cards are disinfected or discarded. */
		plasticKeycardsDisinfected: Option[Boolean] = None,
	  /** Plastic keycards disinfected exception. */
		plasticKeycardsDisinfectedException: Option[Schema.MinimizedContact.PlasticKeycardsDisinfectedExceptionEnum] = None,
	  /** Buffer maintained between room bookings. */
		roomBookingsBuffer: Option[Boolean] = None,
	  /** Room bookings buffer exception. */
		roomBookingsBufferException: Option[Schema.MinimizedContact.RoomBookingsBufferExceptionEnum] = None,
	  /** Housekeeping scheduled by request only. */
		housekeepingScheduledRequestOnly: Option[Boolean] = None,
	  /** Housekeeping scheduled request only exception. */
		housekeepingScheduledRequestOnlyException: Option[Schema.MinimizedContact.HousekeepingScheduledRequestOnlyExceptionEnum] = None,
	  /** No-contact check-in and check-out. */
		contactlessCheckinCheckout: Option[Boolean] = None,
	  /** Contactless check-in check-out exception. */
		contactlessCheckinCheckoutException: Option[Schema.MinimizedContact.ContactlessCheckinCheckoutExceptionEnum] = None
	)
	
	object PersonalProtection {
		enum CommonAreasOfferSanitizingItemsExceptionEnum extends Enum[CommonAreasOfferSanitizingItemsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum GuestRoomHygieneKitsAvailableExceptionEnum extends Enum[GuestRoomHygieneKitsAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ProtectiveEquipmentAvailableExceptionEnum extends Enum[ProtectiveEquipmentAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FaceMaskRequiredExceptionEnum extends Enum[FaceMaskRequiredExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class PersonalProtection(
	  /** Hand-sanitizer and/or sanitizing wipes are offered in common areas. */
		commonAreasOfferSanitizingItems: Option[Boolean] = None,
	  /** Common areas offer sanitizing items exception. */
		commonAreasOfferSanitizingItemsException: Option[Schema.PersonalProtection.CommonAreasOfferSanitizingItemsExceptionEnum] = None,
	  /** In-room hygiene kits with masks, hand sanitizer, and/or antibacterial wipes. */
		guestRoomHygieneKitsAvailable: Option[Boolean] = None,
	  /** Guest room hygiene kits available exception. */
		guestRoomHygieneKitsAvailableException: Option[Schema.PersonalProtection.GuestRoomHygieneKitsAvailableExceptionEnum] = None,
	  /** Masks and/or gloves available for guests. */
		protectiveEquipmentAvailable: Option[Boolean] = None,
	  /** Protective equipment available exception. */
		protectiveEquipmentAvailableException: Option[Schema.PersonalProtection.ProtectiveEquipmentAvailableExceptionEnum] = None,
	  /** Masks required on the property. */
		faceMaskRequired: Option[Boolean] = None,
	  /** Face mask required exception. */
		faceMaskRequiredException: Option[Schema.PersonalProtection.FaceMaskRequiredExceptionEnum] = None
	)
	
	object PhysicalDistancing {
		enum PhysicalDistancingRequiredExceptionEnum extends Enum[PhysicalDistancingRequiredExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SafetyDividersExceptionEnum extends Enum[SafetyDividersExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SharedAreasLimitedOccupancyExceptionEnum extends Enum[SharedAreasLimitedOccupancyExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WellnessAreasHavePrivateSpacesExceptionEnum extends Enum[WellnessAreasHavePrivateSpacesExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CommonAreasPhysicalDistancingArrangedExceptionEnum extends Enum[CommonAreasPhysicalDistancingArrangedExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class PhysicalDistancing(
	  /** Physical distancing required. */
		physicalDistancingRequired: Option[Boolean] = None,
	  /** Physical distancing required exception. */
		physicalDistancingRequiredException: Option[Schema.PhysicalDistancing.PhysicalDistancingRequiredExceptionEnum] = None,
	  /** Safety dividers at front desk and other locations. */
		safetyDividers: Option[Boolean] = None,
	  /** Safety dividers exception. */
		safetyDividersException: Option[Schema.PhysicalDistancing.SafetyDividersExceptionEnum] = None,
	  /** Guest occupancy limited within shared facilities. */
		sharedAreasLimitedOccupancy: Option[Boolean] = None,
	  /** Shared areas limited occupancy exception. */
		sharedAreasLimitedOccupancyException: Option[Schema.PhysicalDistancing.SharedAreasLimitedOccupancyExceptionEnum] = None,
	  /** Private spaces designated in spa and wellness areas. */
		wellnessAreasHavePrivateSpaces: Option[Boolean] = None,
	  /** Wellness areas have private spaces exception. */
		wellnessAreasHavePrivateSpacesException: Option[Schema.PhysicalDistancing.WellnessAreasHavePrivateSpacesExceptionEnum] = None,
	  /** Common areas arranged to maintain physical distancing. */
		commonAreasPhysicalDistancingArranged: Option[Boolean] = None,
	  /** Common areas physical distancing arranged exception. */
		commonAreasPhysicalDistancingArrangedException: Option[Schema.PhysicalDistancing.CommonAreasPhysicalDistancingArrangedExceptionEnum] = None
	)
	
	case class Sustainability(
	  /** Energy efficiency practices implemented at the hotel. */
		energyEfficiency: Option[Schema.EnergyEfficiency] = None,
	  /** Water conservation practices implemented at the hotel. */
		waterConservation: Option[Schema.WaterConservation] = None,
	  /** Waste reduction practices implemented at the hotel. */
		wasteReduction: Option[Schema.WasteReduction] = None,
	  /** Sustainable sourcing practices implemented at the hotel. */
		sustainableSourcing: Option[Schema.SustainableSourcing] = None,
	  /** Sustainability certifications the hotel has been awarded. Deprecated: this field is no longer populated. All certification data is now provided by BeCause. */
		sustainabilityCertifications: Option[Schema.SustainabilityCertifications] = None
	)
	
	object EnergyEfficiency {
		enum GreenBuildingDesignExceptionEnum extends Enum[GreenBuildingDesignExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum EnergyConservationProgramExceptionEnum extends Enum[EnergyConservationProgramExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum IndependentOrganizationAuditsEnergyUseExceptionEnum extends Enum[IndependentOrganizationAuditsEnergyUseExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CarbonFreeEnergySourcesExceptionEnum extends Enum[CarbonFreeEnergySourcesExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum EnergyEfficientHeatingAndCoolingSystemsExceptionEnum extends Enum[EnergyEfficientHeatingAndCoolingSystemsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum EnergyEfficientLightingExceptionEnum extends Enum[EnergyEfficientLightingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum EnergySavingThermostatsExceptionEnum extends Enum[EnergySavingThermostatsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class EnergyEfficiency(
	  /** Output only. Green building design. True if the property has been awarded a relevant certification. */
		greenBuildingDesign: Option[Boolean] = None,
	  /** Output only. Green building design exception. */
		greenBuildingDesignException: Option[Schema.EnergyEfficiency.GreenBuildingDesignExceptionEnum] = None,
	  /** Energy conservation program. The property tracks corporate-level Scope 1 and 2 GHG emissions, and Scope 3 emissions if available. The property has a commitment to implement initiatives that reduce GHG emissions year over year. The property has shown an absolute reduction in emissions for at least 2 years. Emissions are either verfied by a third-party and/or published in external communications. */
		energyConservationProgram: Option[Boolean] = None,
	  /** Energy conservation program exception. */
		energyConservationProgramException: Option[Schema.EnergyEfficiency.EnergyConservationProgramExceptionEnum] = None,
	  /** Independent organization audits energy use. The property conducts an energy audit at least every 5 years, the results of which are either verified by a third-party and/or published in external communications. An energy audit is a detailed assessment of the facility which provides recommendations to existing operations and procedures to improve energy efficiency, available incentives or rebates,and opportunities for improvements through renovations or upgrades. Examples of organizations that conduct credible third party audits include: Engie Impact, DNV GL (EU), Dexma, and local utility providers (they often provide energy and water audits). */
		independentOrganizationAuditsEnergyUse: Option[Boolean] = None,
	  /** Independent organization audits energy use exception. */
		independentOrganizationAuditsEnergyUseException: Option[Schema.EnergyEfficiency.IndependentOrganizationAuditsEnergyUseExceptionEnum] = None,
	  /** Carbon free energy sources. Property sources carbon-free electricity via at least one of the following methods: on-site clean energy generation, power purchase agreement(s) with clean energy generators, green power provided by electricity supplier, or purchases of Energy Attribute Certificates (such as Renewable Energy Certificates or Guarantees of Origin). */
		carbonFreeEnergySources: Option[Boolean] = None,
	  /** Carbon free energy sources exception. */
		carbonFreeEnergySourcesException: Option[Schema.EnergyEfficiency.CarbonFreeEnergySourcesExceptionEnum] = None,
	  /** Energy efficient heating and cooling systems. The property doesn't use chlorofluorocarbon (CFC)-based refrigerants in heating, ventilating, and air-conditioning systems unless a third-party audit shows it's not economically feasible. The CFC-based refrigerants which are used should have a Global Warming Potential (GWP) ≤ 10. The property uses occupancy sensors on HVAC systems in back-of-house spaces, meeting rooms, and other low-traffic areas. */
		energyEfficientHeatingAndCoolingSystems: Option[Boolean] = None,
	  /** Energy efficient heating and cooling systems exception. */
		energyEfficientHeatingAndCoolingSystemsException: Option[Schema.EnergyEfficiency.EnergyEfficientHeatingAndCoolingSystemsExceptionEnum] = None,
	  /** Energy efficient lighting. At least 75% of the property's lighting is energy efficient, using lighting that is more than 45 lumens per watt – typically LED or CFL lightbulbs. */
		energyEfficientLighting: Option[Boolean] = None,
	  /** Energy efficient lighting exception. */
		energyEfficientLightingException: Option[Schema.EnergyEfficiency.EnergyEfficientLightingExceptionEnum] = None,
	  /** Energy saving thermostats. The property installed energy-saving thermostats throughout the building to conserve energy when rooms or areas are not in use. Energy-saving thermostats are devices that control heating/cooling in the building by learning temperature preferences and automatically adjusting to energy-saving temperatures as the default. The thermostats are automatically set to a temperature between 68-78 degrees F (20-26 °C), depending on seasonality. In the winter, set the thermostat to 68°F (20°C) when the room is occupied, lowering room temperature when unoccupied. In the summer, set the thermostat to 78°F (26°C) when the room is occupied. */
		energySavingThermostats: Option[Boolean] = None,
	  /** Energy saving thermostats exception. */
		energySavingThermostatsException: Option[Schema.EnergyEfficiency.EnergySavingThermostatsExceptionEnum] = None
	)
	
	object WaterConservation {
		enum IndependentOrganizationAuditsWaterUseExceptionEnum extends Enum[IndependentOrganizationAuditsWaterUseExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WaterSavingSinksExceptionEnum extends Enum[WaterSavingSinksExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WaterSavingToiletsExceptionEnum extends Enum[WaterSavingToiletsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WaterSavingShowersExceptionEnum extends Enum[WaterSavingShowersExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TowelReuseProgramExceptionEnum extends Enum[TowelReuseProgramExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum LinenReuseProgramExceptionEnum extends Enum[LinenReuseProgramExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class WaterConservation(
	  /** Independent organization audits water use. The property conducts a water conservation audit every 5 years, the results of which are either verified by a third-party and/or published in external communications. A water conservation audit is a detailed assessment of the facility, providing recommendations to existing operations and procedures to improve water efficiency, available incentives or rebates, and opportunities for improvements through renovations or upgrades. Examples of organizations who conduct credible third party audits include: Engie Impact, and local utility providers (they often provide energy and water audits). */
		independentOrganizationAuditsWaterUse: Option[Boolean] = None,
	  /** Independent organization audits water use exception. */
		independentOrganizationAuditsWaterUseException: Option[Schema.WaterConservation.IndependentOrganizationAuditsWaterUseExceptionEnum] = None,
	  /** Water saving sinks. All of the property's guest rooms have bathroom faucets that use a maximum of 1.5 gallons per minute (gpm), public restroom faucets do not exceed 0.5 gpm, and kitchen faucets (excluding faucets used exclusively for filling operations) do not exceed 2.2 gpm. */
		waterSavingSinks: Option[Boolean] = None,
	  /** Water saving sinks exception. */
		waterSavingSinksException: Option[Schema.WaterConservation.WaterSavingSinksExceptionEnum] = None,
	  /** Water saving toilets. All of the property's toilets use 1.6 gallons per flush, or less. */
		waterSavingToilets: Option[Boolean] = None,
	  /** Water saving toilets exception. */
		waterSavingToiletsException: Option[Schema.WaterConservation.WaterSavingToiletsExceptionEnum] = None,
	  /** Water saving showers. All of the property's guest rooms have shower heads that use no more than 2.0 gallons per minute (gpm). */
		waterSavingShowers: Option[Boolean] = None,
	  /** Water saving showers exception. */
		waterSavingShowersException: Option[Schema.WaterConservation.WaterSavingShowersExceptionEnum] = None,
	  /** Towel reuse program. The property offers a towel reuse program. */
		towelReuseProgram: Option[Boolean] = None,
	  /** Towel reuse program exception. */
		towelReuseProgramException: Option[Schema.WaterConservation.TowelReuseProgramExceptionEnum] = None,
	  /** Linen reuse program. The property offers a linen reuse program. */
		linenReuseProgram: Option[Boolean] = None,
	  /** Linen reuse program exception. */
		linenReuseProgramException: Option[Schema.WaterConservation.LinenReuseProgramExceptionEnum] = None
	)
	
	object WasteReduction {
		enum RecyclingProgramExceptionEnum extends Enum[RecyclingProgramExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FoodWasteReductionProgramExceptionEnum extends Enum[FoodWasteReductionProgramExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DonatesExcessFoodExceptionEnum extends Enum[DonatesExcessFoodExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CompostsExcessFoodExceptionEnum extends Enum[CompostsExcessFoodExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SoapDonationProgramExceptionEnum extends Enum[SoapDonationProgramExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ToiletryDonationProgramExceptionEnum extends Enum[ToiletryDonationProgramExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SafelyHandlesHazardousSubstancesExceptionEnum extends Enum[SafelyHandlesHazardousSubstancesExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SafelyDisposesElectronicsExceptionEnum extends Enum[SafelyDisposesElectronicsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SafelyDisposesBatteriesExceptionEnum extends Enum[SafelyDisposesBatteriesExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SafelyDisposesLightbulbsExceptionEnum extends Enum[SafelyDisposesLightbulbsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum RefillableToiletryContainersExceptionEnum extends Enum[RefillableToiletryContainersExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WaterBottleFillingStationsExceptionEnum extends Enum[WaterBottleFillingStationsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CompostableFoodContainersAndCutleryExceptionEnum extends Enum[CompostableFoodContainersAndCutleryExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum NoStyrofoamFoodContainersExceptionEnum extends Enum[NoStyrofoamFoodContainersExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum NoSingleUsePlasticWaterBottlesExceptionEnum extends Enum[NoSingleUsePlasticWaterBottlesExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum NoSingleUsePlasticStrawsExceptionEnum extends Enum[NoSingleUsePlasticStrawsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class WasteReduction(
	  /** Recycling program. The property has a recycling program, aligned with LEED waste requirements, and a policy outlining efforts to send less than 50% of waste to landfill. The recycling program includes storage locations for recyclable materials, including mixed paper, corrugated cardboard, glass, plastics, and metals. */
		recyclingProgram: Option[Boolean] = None,
	  /** Recycling program exception. */
		recyclingProgramException: Option[Schema.WasteReduction.RecyclingProgramExceptionEnum] = None,
	  /** Food waste reduction program. The property has established a food waste reduction and donation program, aiming to reduce food waste by half. These programs typically use tools such as the Hotel Kitchen Toolkit and others to track waste and measure progress. */
		foodWasteReductionProgram: Option[Boolean] = None,
	  /** Food waste reduction program exception. */
		foodWasteReductionProgramException: Option[Schema.WasteReduction.FoodWasteReductionProgramExceptionEnum] = None,
	  /** Donates excess food. The property has a program and/or policy for diverting waste from landfill that may include efforts to donate for human consumption or divert food for animal feed. */
		donatesExcessFood: Option[Boolean] = None,
	  /** Donates excess food exception. */
		donatesExcessFoodException: Option[Schema.WasteReduction.DonatesExcessFoodExceptionEnum] = None,
	  /** Composts excess food. The property has a program and/or policy for diverting waste from landfill by composting food and yard waste, either through compost collection and off-site processing or on-site compost processing. */
		compostsExcessFood: Option[Boolean] = None,
	  /** Composts excess food exception. */
		compostsExcessFoodException: Option[Schema.WasteReduction.CompostsExcessFoodExceptionEnum] = None,
	  /** Soap donation program. The property participates in a soap donation program such as Clean the World or something similar. */
		soapDonationProgram: Option[Boolean] = None,
	  /** Soap donation program exception. */
		soapDonationProgramException: Option[Schema.WasteReduction.SoapDonationProgramExceptionEnum] = None,
	  /** Toiletry donation program. The property participates in a toiletry donation program such as Clean the World or something similar. */
		toiletryDonationProgram: Option[Boolean] = None,
	  /** Toiletry donation program exception. */
		toiletryDonationProgramException: Option[Schema.WasteReduction.ToiletryDonationProgramExceptionEnum] = None,
	  /** Safely handles hazardous substances. The property has a hazardous waste management program aligned wit GreenSeal and LEED requirements, and meets all regulatory requirements for hazardous waste disposal and recycling. Hazardous means substances that are classified as "hazardous" by an authoritative body (such as OSHA or DOT), are labeled with signal words such as "Danger," "Caution," "Warning," or are flammable, corrosive, or ignitable. Requirements include: - The property shall maintain records of the efforts it has made to replace the hazardous substances it uses with less hazardous alternatives. - An inventory of the hazardous materials stored on-site. - Products intended for cleaning, dishwashing, laundry, and pool maintenance shall be stored in clearly labeled containers. These containers shall be checked regularly for leaks, and replaced a necessary. - Spill containment devices shall be installed to collect spills, drips, or leaching of chemicals. */
		safelyHandlesHazardousSubstances: Option[Boolean] = None,
	  /** Safely handles hazardous substances exception. */
		safelyHandlesHazardousSubstancesException: Option[Schema.WasteReduction.SafelyHandlesHazardousSubstancesExceptionEnum] = None,
	  /** Safely disposes electronics. The property has a reputable recycling program that keeps hazardous electronic parts and chemical compounds out of landfills, dumps and other unauthorized abandonment sites, and recycles/reuses applicable materials. (e.g. certified electronics recyclers). */
		safelyDisposesElectronics: Option[Boolean] = None,
	  /** Safely disposes electronics exception. */
		safelyDisposesElectronicsException: Option[Schema.WasteReduction.SafelyDisposesElectronicsExceptionEnum] = None,
	  /** Safely disposes batteries. The property safely stores and disposes batteries. */
		safelyDisposesBatteries: Option[Boolean] = None,
	  /** Safely disposes batteries exception. */
		safelyDisposesBatteriesException: Option[Schema.WasteReduction.SafelyDisposesBatteriesExceptionEnum] = None,
	  /** Safely disposes lightbulbs. The property safely stores and disposes lightbulbs. */
		safelyDisposesLightbulbs: Option[Boolean] = None,
	  /** Safely disposes lightbulbs exception. */
		safelyDisposesLightbulbsException: Option[Schema.WasteReduction.SafelyDisposesLightbulbsExceptionEnum] = None,
	  /** Refillable toiletry containers. The property has replaced miniature individual containers with refillable amenity dispensers for shampoo, conditioner, soap, and lotion. */
		refillableToiletryContainers: Option[Boolean] = None,
	  /** Refillable toiletry containers exception. */
		refillableToiletryContainersException: Option[Schema.WasteReduction.RefillableToiletryContainersExceptionEnum] = None,
	  /** Water bottle filling stations. The property offers water stations throughout the building for guest use. */
		waterBottleFillingStations: Option[Boolean] = None,
	  /** Water bottle filling stations exception. */
		waterBottleFillingStationsException: Option[Schema.WasteReduction.WaterBottleFillingStationsExceptionEnum] = None,
	  /** Compostable food containers and cutlery. 100% of food service containers and to-go cutlery are compostable, and reusable utensils are offered wherever possible. Compostable materials are capable of undergoing biological decomposition in a compost site, such that material is not visually distinguishable and breaks down into carbon dioxide, water, inorganic compounds, and biomass. */
		compostableFoodContainersAndCutlery: Option[Boolean] = None,
	  /** Compostable food containers and cutlery exception. */
		compostableFoodContainersAndCutleryException: Option[Schema.WasteReduction.CompostableFoodContainersAndCutleryExceptionEnum] = None,
	  /** No styrofoam food containers. The property eliminates the use of Styrofoam in disposable food service items. */
		noStyrofoamFoodContainers: Option[Boolean] = None,
	  /** No styrofoam food containers exception. */
		noStyrofoamFoodContainersException: Option[Schema.WasteReduction.NoStyrofoamFoodContainersExceptionEnum] = None,
	  /** No single use plastic water bottles. The property bans single-use plastic water bottles. */
		noSingleUsePlasticWaterBottles: Option[Boolean] = None,
	  /** No single use plastic water bottles exception. */
		noSingleUsePlasticWaterBottlesException: Option[Schema.WasteReduction.NoSingleUsePlasticWaterBottlesExceptionEnum] = None,
	  /** No single use plastic straws. The property bans single-use plastic straws. */
		noSingleUsePlasticStraws: Option[Boolean] = None,
	  /** No single use plastic straws exception. */
		noSingleUsePlasticStrawsException: Option[Schema.WasteReduction.NoSingleUsePlasticStrawsExceptionEnum] = None
	)
	
	object SustainableSourcing {
		enum ResponsiblePurchasingPolicyExceptionEnum extends Enum[ResponsiblePurchasingPolicyExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum OrganicFoodAndBeveragesExceptionEnum extends Enum[OrganicFoodAndBeveragesExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum LocallySourcedFoodAndBeveragesExceptionEnum extends Enum[LocallySourcedFoodAndBeveragesExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ResponsiblySourcesSeafoodExceptionEnum extends Enum[ResponsiblySourcesSeafoodExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum OrganicCageFreeEggsExceptionEnum extends Enum[OrganicCageFreeEggsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum VegetarianMealsExceptionEnum extends Enum[VegetarianMealsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum VeganMealsExceptionEnum extends Enum[VeganMealsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum EcoFriendlyToiletriesExceptionEnum extends Enum[EcoFriendlyToiletriesExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class SustainableSourcing(
	  /** Responsible purchasing policy. The property has a responsible procurement policy in place. Responsible means integration of social, ethical, and/or environmental performance factors into the procurement process when selecting suppliers. */
		responsiblePurchasingPolicy: Option[Boolean] = None,
	  /** Responsible purchasing policy exception. */
		responsiblePurchasingPolicyException: Option[Schema.SustainableSourcing.ResponsiblePurchasingPolicyExceptionEnum] = None,
	  /** Organic food and beverages. At least 25% of food and beverages, by spend, are certified organic. Organic means products that are certified to one of the organic standard listed in the IFOAM family of standards. Qualifying certifications include USDA Organic and EU Organic, among others. */
		organicFoodAndBeverages: Option[Boolean] = None,
	  /** Organic food and beverages exception. */
		organicFoodAndBeveragesException: Option[Schema.SustainableSourcing.OrganicFoodAndBeveragesExceptionEnum] = None,
	  /** Locally sourced food and beverages. Property sources locally in order to lower the environmental footprint from reduced transportation and to stimulate the local economy. Products produced less than 62 miles from the establishment are normally considered as locally produced. */
		locallySourcedFoodAndBeverages: Option[Boolean] = None,
	  /** Locally sourced food and beverages exception. */
		locallySourcedFoodAndBeveragesException: Option[Schema.SustainableSourcing.LocallySourcedFoodAndBeveragesExceptionEnum] = None,
	  /** Responsibly sources seafood. The property does not source seafood from the Monterey Bay Aquarium Seafood Watch "avoid" list, and must sustainably source seafood listed as "good alternative," "eco-certified," and "best choice". The property has a policy outlining a commitment to source Marine Stewardship Council (MSC) and/or Aquaculture Stewardship Council (ASC) Chain of Custody certified seafood. */
		responsiblySourcesSeafood: Option[Boolean] = None,
	  /** Responsibly sources seafood exception. */
		responsiblySourcesSeafoodException: Option[Schema.SustainableSourcing.ResponsiblySourcesSeafoodExceptionEnum] = None,
	  /** Organic cage free eggs. The property sources 100% certified organic and cage-free eggs (shell, liquid, and egg products). Cage-free means hens are able to walk, spread their wings and lay their eggs in nests). */
		organicCageFreeEggs: Option[Boolean] = None,
	  /** Organic cage free eggs exception. */
		organicCageFreeEggsException: Option[Schema.SustainableSourcing.OrganicCageFreeEggsExceptionEnum] = None,
	  /** Vegetarian meals. The property provides vegetarian menu options for guests. Vegetarian food does not contain meat, poultry, fish, or seafood. */
		vegetarianMeals: Option[Boolean] = None,
	  /** Vegetarian meals exception. */
		vegetarianMealsException: Option[Schema.SustainableSourcing.VegetarianMealsExceptionEnum] = None,
	  /** Vegan meals. The property provides vegan menu options for guests. Vegan food does not contain animal products or byproducts. */
		veganMeals: Option[Boolean] = None,
	  /** Vegan meals exception. */
		veganMealsException: Option[Schema.SustainableSourcing.VeganMealsExceptionEnum] = None,
	  /** Eco friendly toiletries. Soap, shampoo, lotion, and other toiletries provided for guests have a nationally or internationally recognized sustainability certification, such as USDA Organic, EU Organic, or cruelty-free. */
		ecoFriendlyToiletries: Option[Boolean] = None,
	  /** Eco friendly toiletries exception. */
		ecoFriendlyToiletriesException: Option[Schema.SustainableSourcing.EcoFriendlyToiletriesExceptionEnum] = None
	)
	
	object SustainabilityCertifications {
		enum BreeamCertificationEnum extends Enum[BreeamCertificationEnum] { case BREEAM_CERTIFICATION_UNSPECIFIED, NO_BREEAM_CERTIFICATION, BREEAM_PASS, BREEAM_GOOD, BREEAM_VERY_GOOD, BREEAM_EXCELLENT, BREEAM_OUTSTANDING }
		enum BreeamCertificationExceptionEnum extends Enum[BreeamCertificationExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum LeedCertificationEnum extends Enum[LeedCertificationEnum] { case LEED_CERTIFICATION_UNSPECIFIED, NO_LEED_CERTIFICATION, LEED_CERTIFIED, LEED_SILVER, LEED_GOLD, LEED_PLATINUM }
		enum LeedCertificationExceptionEnum extends Enum[LeedCertificationExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class SustainabilityCertifications(
	  /** BREEAM certification. */
		breeamCertification: Option[Schema.SustainabilityCertifications.BreeamCertificationEnum] = None,
	  /** BREEAM certification exception. */
		breeamCertificationException: Option[Schema.SustainabilityCertifications.BreeamCertificationExceptionEnum] = None,
	  /** LEED certification. */
		leedCertification: Option[Schema.SustainabilityCertifications.LeedCertificationEnum] = None,
	  /** LEED certification exception. */
		leedCertificationException: Option[Schema.SustainabilityCertifications.LeedCertificationExceptionEnum] = None,
	  /** The eco certificates awarded to the hotel. */
		ecoCertifications: Option[List[Schema.EcoCertification]] = None
	)
	
	object EcoCertification {
		enum EcoCertificateEnum extends Enum[EcoCertificateEnum] { case ECO_CERTIFICATE_UNSPECIFIED, ISO14001, ISO50001, ASIAN_ECOTOURISM, BIOSPHERE_RESPOSNIBLE_TOURISM, BUREAU_VERITAS, CONTROL_UNION, EARTHCHECK, ECO_CERTIFICATION_MALTA, ECOTOURISM_AUSTRALIAS_ECO, GREAT_GREEN_DEAL, GREEN_GLOBE, GREEN_GROWTH2050, GREEN_KEY, GREEN_KEY_ECO_RATING, GREEN_SEAL, GREEN_STAR, GREEN_TOURISM_ACTIVE, HILTON_LIGHTSTAY, HOSTELLING_INTERNATIONALS_QUALITY_AND_SUSTAINABILITY, HOTELES_MAS_VERDES, NORDIC_SWAN_ECOLABEL, PREFERRED_BY_NATURE_SUSTAINABLE_TOURISM, SUSTAINABLE_TRAVEL_IRELAND, TOF_TIGERS_INITITIVES_PUG, TRAVELIFE, UNITED_CERTIFICATION_SYSTEMS_LIMITED, VIREO_SRL }
		enum AwardedExceptionEnum extends Enum[AwardedExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class EcoCertification(
	  /** Required. The eco certificate. */
		ecoCertificate: Option[Schema.EcoCertification.EcoCertificateEnum] = None,
	  /** Whether the eco certificate was awarded or not. */
		awarded: Option[Boolean] = None,
	  /** Awarded exception. */
		awardedException: Option[Schema.EcoCertification.AwardedExceptionEnum] = None
	)
	
	case class LivingArea(
	  /** Information about the layout of the living area. */
		layout: Option[Schema.LivingAreaLayout] = None,
	  /** Features in the living area. */
		features: Option[Schema.LivingAreaFeatures] = None,
	  /** Information about eating features in the living area. */
		eating: Option[Schema.LivingAreaEating] = None,
	  /** Information about sleeping features in the living area. */
		sleeping: Option[Schema.LivingAreaSleeping] = None,
	  /** Accessibility features of the living area. */
		accessibility: Option[Schema.LivingAreaAccessibility] = None
	)
	
	object LivingAreaLayout {
		enum LivingAreaSqMetersExceptionEnum extends Enum[LivingAreaSqMetersExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum StairsExceptionEnum extends Enum[StairsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum LoftExceptionEnum extends Enum[LoftExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum NonSmokingExceptionEnum extends Enum[NonSmokingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PatioExceptionEnum extends Enum[PatioExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BalconyExceptionEnum extends Enum[BalconyExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class LivingAreaLayout(
	  /** Living area sq meters. The measurement in meters of the area of a guestroom's living space. */
		livingAreaSqMeters: Option[BigDecimal] = None,
	  /** Living area sq meters exception. */
		livingAreaSqMetersException: Option[Schema.LivingAreaLayout.LivingAreaSqMetersExceptionEnum] = None,
	  /** Stairs. There are steps leading from one level or story to another in the unit. */
		stairs: Option[Boolean] = None,
	  /** Stairs exception. */
		stairsException: Option[Schema.LivingAreaLayout.StairsExceptionEnum] = None,
	  /** Loft. A three-walled upper area accessed by stairs or a ladder that overlooks the lower area of a room. */
		loft: Option[Boolean] = None,
	  /** Loft exception. */
		loftException: Option[Schema.LivingAreaLayout.LoftExceptionEnum] = None,
	  /** Non smoking. A guestroom in which the smoking of cigarettes, cigars and pipes is prohibited. */
		nonSmoking: Option[Boolean] = None,
	  /** Non smoking exception. */
		nonSmokingException: Option[Schema.LivingAreaLayout.NonSmokingExceptionEnum] = None,
	  /** Patio. A paved, outdoor area with seating attached to and accessed through a ground-floor guestroom for use by the occupants of the guestroom. */
		patio: Option[Boolean] = None,
	  /** Patio exception. */
		patioException: Option[Schema.LivingAreaLayout.PatioExceptionEnum] = None,
	  /** Balcony. An outdoor platform attached to a building and surrounded by a short wall, fence or other safety railing. The balcony is accessed through a door in a guestroom or suite and is for use by the guest staying in that room. May or may not include seating or outdoor furniture. Is not located on the ground floor. Also lanai. */
		balcony: Option[Boolean] = None,
	  /** Balcony exception. */
		balconyException: Option[Schema.LivingAreaLayout.BalconyExceptionEnum] = None
	)
	
	object LivingAreaFeatures {
		enum PrivateBathroomExceptionEnum extends Enum[PrivateBathroomExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ToiletExceptionEnum extends Enum[ToiletExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BidetExceptionEnum extends Enum[BidetExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ShowerExceptionEnum extends Enum[ShowerExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BathtubExceptionEnum extends Enum[BathtubExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum HairdryerExceptionEnum extends Enum[HairdryerExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum WasherExceptionEnum extends Enum[WasherExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DryerExceptionEnum extends Enum[DryerExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum IroningEquipmentExceptionEnum extends Enum[IroningEquipmentExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum UniversalPowerAdaptersExceptionEnum extends Enum[UniversalPowerAdaptersExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum AirConditioningExceptionEnum extends Enum[AirConditioningExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum HeatingExceptionEnum extends Enum[HeatingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FireplaceExceptionEnum extends Enum[FireplaceExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TvExceptionEnum extends Enum[TvExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TvCastingExceptionEnum extends Enum[TvCastingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TvStreamingExceptionEnum extends Enum[TvStreamingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PayPerViewMoviesExceptionEnum extends Enum[PayPerViewMoviesExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum InunitSafeExceptionEnum extends Enum[InunitSafeExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ElectronicRoomKeyExceptionEnum extends Enum[ElectronicRoomKeyExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum InunitWifiAvailableExceptionEnum extends Enum[InunitWifiAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class LivingAreaFeatures(
	  /** Private bathroom. A bathroom designated for the express use of the guests staying in a specific guestroom. */
		privateBathroom: Option[Boolean] = None,
	  /** Private bathroom exception. */
		privateBathroomException: Option[Schema.LivingAreaFeatures.PrivateBathroomExceptionEnum] = None,
	  /** Toilet. A fixed bathroom feature connected to a sewer or septic system and consisting of a water-flushed bowl with a seat, as well as a device that elicites the water-flushing action. Used for the process and disposal of human waste. */
		toilet: Option[Boolean] = None,
	  /** Toilet exception. */
		toiletException: Option[Schema.LivingAreaFeatures.ToiletExceptionEnum] = None,
	  /** Bidet. A plumbing fixture attached to a toilet or a low, fixed sink designed for the purpose of washing after toilet use. */
		bidet: Option[Boolean] = None,
	  /** Bidet exception. */
		bidetException: Option[Schema.LivingAreaFeatures.BidetExceptionEnum] = None,
	  /** Shower. A fixed plumbing fixture for standing bathing that features a tall spray spout or faucet through which water flows, a knob or knobs that control the water's temperature, and a drain in the floor. */
		shower: Option[Boolean] = None,
	  /** Shower exception. */
		showerException: Option[Schema.LivingAreaFeatures.ShowerExceptionEnum] = None,
	  /** Bathtub. A fixed plumbing feature set on the floor and consisting of a large container that accommodates the body of an adult for the purpose of seated bathing. Includes knobs or fixtures to control the temperature of the water, a faucet through which the water flows, and a drain that can be closed for filling and opened for draining. */
		bathtub: Option[Boolean] = None,
	  /** Bathtub exception. */
		bathtubException: Option[Schema.LivingAreaFeatures.BathtubExceptionEnum] = None,
	  /** Hairdryer. A handheld electric appliance that blows temperature-controlled air for the purpose of drying wet hair. Can be mounted to a bathroom wall or a freestanding device stored in the guestroom's bathroom or closet. */
		hairdryer: Option[Boolean] = None,
	  /** Hairdryer exception. */
		hairdryerException: Option[Schema.LivingAreaFeatures.HairdryerExceptionEnum] = None,
	  /** Washer. An electrical machine connected to a running water source designed to launder clothing. */
		washer: Option[Boolean] = None,
	  /** Washer exception. */
		washerException: Option[Schema.LivingAreaFeatures.WasherExceptionEnum] = None,
	  /** Dryer. An electrical machine designed to dry clothing. */
		dryer: Option[Boolean] = None,
	  /** Dryer exception. */
		dryerException: Option[Schema.LivingAreaFeatures.DryerExceptionEnum] = None,
	  /** Ironing equipment. A device, usually with a flat metal base, that is heated to smooth, finish, or press clothes and a flat, padded, cloth-covered surface on which the clothes are worked. */
		ironingEquipment: Option[Boolean] = None,
	  /** Ironing equipment exception. */
		ironingEquipmentException: Option[Schema.LivingAreaFeatures.IroningEquipmentExceptionEnum] = None,
	  /** Universal power adapters. A power supply for electronic devices which plugs into a wall for the purpose of converting AC to a single DC voltage. Also know as AC adapter or charger. */
		universalPowerAdapters: Option[Boolean] = None,
	  /** Universal power adapters exception. */
		universalPowerAdaptersException: Option[Schema.LivingAreaFeatures.UniversalPowerAdaptersExceptionEnum] = None,
	  /** Air conditioning. An electrical machine used to cool the temperature of the guestroom. */
		airConditioning: Option[Boolean] = None,
	  /** Air conditioning exception. */
		airConditioningException: Option[Schema.LivingAreaFeatures.AirConditioningExceptionEnum] = None,
	  /** Heating. An electrical machine used to warm the temperature of the guestroom. */
		heating: Option[Boolean] = None,
	  /** Heating exception. */
		heatingException: Option[Schema.LivingAreaFeatures.HeatingExceptionEnum] = None,
	  /** Fireplace. A framed opening (aka hearth) at the base of a chimney in which logs or an electrical fire feature are burned to provide a relaxing ambiance or to heat the room. Often made of bricks or stone. */
		fireplace: Option[Boolean] = None,
	  /** Fireplace exception. */
		fireplaceException: Option[Schema.LivingAreaFeatures.FireplaceExceptionEnum] = None,
	  /** TV. A television is available in the guestroom. */
		tv: Option[Boolean] = None,
	  /** TV exception. */
		tvException: Option[Schema.LivingAreaFeatures.TvExceptionEnum] = None,
	  /** TV casting. A television equipped with a device through which the video entertainment accessed on a personal computer, phone or tablet can be wirelessly delivered to and viewed on the guestroom's television. */
		tvCasting: Option[Boolean] = None,
	  /** TV exception. */
		tvCastingException: Option[Schema.LivingAreaFeatures.TvCastingExceptionEnum] = None,
	  /** TV streaming. Televisions that embed a range of web-based apps to allow for watching media from those apps. */
		tvStreaming: Option[Boolean] = None,
	  /** TV streaming exception. */
		tvStreamingException: Option[Schema.LivingAreaFeatures.TvStreamingExceptionEnum] = None,
	  /** Pay per view movies. Televisions with channels that offer films that can be viewed for a fee, and have an interface to allow the viewer to accept the terms and approve payment. */
		payPerViewMovies: Option[Boolean] = None,
	  /** Pay per view movies exception. */
		payPerViewMoviesException: Option[Schema.LivingAreaFeatures.PayPerViewMoviesExceptionEnum] = None,
	  /** In-unit safe. A strong fireproof cabinet with a programmable lock, used for the protected storage of valuables in a guestroom. Often built into a closet. */
		inunitSafe: Option[Boolean] = None,
	  /** In-unit safe exception. */
		inunitSafeException: Option[Schema.LivingAreaFeatures.InunitSafeExceptionEnum] = None,
	  /** Electronic room key. A card coded by the check-in computer that is read by the lock on the hotel guestroom door to allow for entry. */
		electronicRoomKey: Option[Boolean] = None,
	  /** Electronic room key exception. */
		electronicRoomKeyException: Option[Schema.LivingAreaFeatures.ElectronicRoomKeyExceptionEnum] = None,
	  /** In-unit Wifi available. Guests can wirelessly connect to the Internet in the guestroom. Can be free or for a fee. */
		inunitWifiAvailable: Option[Boolean] = None,
	  /** In-unit Wifi available exception. */
		inunitWifiAvailableException: Option[Schema.LivingAreaFeatures.InunitWifiAvailableExceptionEnum] = None
	)
	
	object LivingAreaEating {
		enum KitchenAvailableExceptionEnum extends Enum[KitchenAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum RefrigeratorExceptionEnum extends Enum[RefrigeratorExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DishwasherExceptionEnum extends Enum[DishwasherExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum StoveExceptionEnum extends Enum[StoveExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum OvenExceptionEnum extends Enum[OvenExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CookwareExceptionEnum extends Enum[CookwareExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SinkExceptionEnum extends Enum[SinkExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MicrowaveExceptionEnum extends Enum[MicrowaveExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ToasterExceptionEnum extends Enum[ToasterExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum IndoorGrillExceptionEnum extends Enum[IndoorGrillExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum OutdoorGrillExceptionEnum extends Enum[OutdoorGrillExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MinibarExceptionEnum extends Enum[MinibarExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SnackbarExceptionEnum extends Enum[SnackbarExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CoffeeMakerExceptionEnum extends Enum[CoffeeMakerExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum KettleExceptionEnum extends Enum[KettleExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum TeaStationExceptionEnum extends Enum[TeaStationExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class LivingAreaEating(
	  /** Kitchen available. An area of the guestroom designated for the preparation and storage of food via the presence of a refrigerator, cook top, oven and sink, as well as cutlery, dishes and cookware. Usually includes small appliances such a coffee maker and a microwave. May or may not include an automatic dishwasher. */
		kitchenAvailable: Option[Boolean] = None,
	  /** Kitchen available exception. */
		kitchenAvailableException: Option[Schema.LivingAreaEating.KitchenAvailableExceptionEnum] = None,
	  /** Refrigerator. A large, climate-controlled electrical cabinet with vertical doors. Built for the purpose of chilling and storing perishable foods. */
		refrigerator: Option[Boolean] = None,
	  /** Refrigerator exception. */
		refrigeratorException: Option[Schema.LivingAreaEating.RefrigeratorExceptionEnum] = None,
	  /** Dishwasher. A counter-height electrical cabinet containing racks for dirty dishware, cookware and cutlery, and a dispenser for soap built into the pull-down door. The cabinet is attached to the plumbing system to facilitate the automatic cleaning of its contents. */
		dishwasher: Option[Boolean] = None,
	  /** Dishwasher exception. */
		dishwasherException: Option[Schema.LivingAreaEating.DishwasherExceptionEnum] = None,
	  /** Stove. A kitchen appliance powered by gas or electricity for the purpose of creating a flame or hot surface on which pots of food can be cooked. Also known as cooktop or hob. */
		stove: Option[Boolean] = None,
	  /** Stove exception. */
		stoveException: Option[Schema.LivingAreaEating.StoveExceptionEnum] = None,
	  /** Oven. A temperature controlled, heated metal cabinet powered by gas or electricity in which food is placed for the purpose of cooking or reheating. */
		oven: Option[Boolean] = None,
	  /** Oven exception. */
		ovenException: Option[Schema.LivingAreaEating.OvenExceptionEnum] = None,
	  /** Cookware. Kitchen pots, pans and utensils used in connection with the preparation of food. */
		cookware: Option[Boolean] = None,
	  /** Cookware exception. */
		cookwareException: Option[Schema.LivingAreaEating.CookwareExceptionEnum] = None,
	  /** Sink. A basin with a faucet attached to a water source and used for the purpose of washing and rinsing. */
		sink: Option[Boolean] = None,
	  /** Sink exception. */
		sinkException: Option[Schema.LivingAreaEating.SinkExceptionEnum] = None,
	  /** Microwave. An electric oven that quickly cooks and heats food by microwave energy. Smaller than a standing or wall mounted oven. Usually placed on a kitchen counter, a shelf or tabletop or mounted above a cooktop. */
		microwave: Option[Boolean] = None,
	  /** Microwave exception. */
		microwaveException: Option[Schema.LivingAreaEating.MicrowaveExceptionEnum] = None,
	  /** Toaster. A small, temperature controlled electric appliance with rectangular slots at the top that are lined with heated coils for the purpose of browning slices of bread products. */
		toaster: Option[Boolean] = None,
	  /** Toaster exception. */
		toasterException: Option[Schema.LivingAreaEating.ToasterExceptionEnum] = None,
	  /** Indoor grill. Metal grates built into an indoor cooktop on which food is cooked over an open flame or electric heat source. */
		indoorGrill: Option[Boolean] = None,
	  /** Indoor grill exception. */
		indoorGrillException: Option[Schema.LivingAreaEating.IndoorGrillExceptionEnum] = None,
	  /** Outdoor grill. Metal grates on which food is cooked over an open flame or electric heat source. Part of an outdoor apparatus that supports the grates. Also known as barbecue grill or barbecue. */
		outdoorGrill: Option[Boolean] = None,
	  /** Outdoor grill exception. */
		outdoorGrillException: Option[Schema.LivingAreaEating.OutdoorGrillExceptionEnum] = None,
	  /** Minibar. A small refrigerated cabinet in the guestroom containing bottles/cans of soft drinks, mini bottles of alcohol, and snacks. The items are most commonly available for a fee. */
		minibar: Option[Boolean] = None,
	  /** Minibar exception. */
		minibarException: Option[Schema.LivingAreaEating.MinibarExceptionEnum] = None,
	  /** Snackbar. A small cabinet in the guestroom containing snacks. The items are most commonly available for a fee. */
		snackbar: Option[Boolean] = None,
	  /** Snackbar exception. */
		snackbarException: Option[Schema.LivingAreaEating.SnackbarExceptionEnum] = None,
	  /** Coffee maker. An electric appliance that brews coffee by heating and forcing water through ground coffee. */
		coffeeMaker: Option[Boolean] = None,
	  /** Coffee maker exception. */
		coffeeMakerException: Option[Schema.LivingAreaEating.CoffeeMakerExceptionEnum] = None,
	  /** Kettle. A covered container with a handle and a spout used for boiling water. */
		kettle: Option[Boolean] = None,
	  /** Kettle exception. */
		kettleException: Option[Schema.LivingAreaEating.KettleExceptionEnum] = None,
	  /** Tea station. A small area with the supplies needed to heat water and make tea. */
		teaStation: Option[Boolean] = None,
	  /** Tea station exception. */
		teaStationException: Option[Schema.LivingAreaEating.TeaStationExceptionEnum] = None
	)
	
	object LivingAreaSleeping {
		enum BedsCountExceptionEnum extends Enum[BedsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum KingBedsCountExceptionEnum extends Enum[KingBedsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum QueenBedsCountExceptionEnum extends Enum[QueenBedsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum DoubleBedsCountExceptionEnum extends Enum[DoubleBedsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SingleOrTwinBedsCountExceptionEnum extends Enum[SingleOrTwinBedsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SofaBedsCountExceptionEnum extends Enum[SofaBedsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BunkBedsCountExceptionEnum extends Enum[BunkBedsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum OtherBedsCountExceptionEnum extends Enum[OtherBedsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum RollAwayBedsCountExceptionEnum extends Enum[RollAwayBedsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CribsCountExceptionEnum extends Enum[CribsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum HypoallergenicBeddingExceptionEnum extends Enum[HypoallergenicBeddingExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SyntheticPillowsExceptionEnum extends Enum[SyntheticPillowsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MemoryFoamPillowsExceptionEnum extends Enum[MemoryFoamPillowsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum FeatherPillowsExceptionEnum extends Enum[FeatherPillowsExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class LivingAreaSleeping(
	  /** Beds count. The number of permanent beds present in a guestroom. Does not include rollaway beds, cribs or sofabeds. */
		bedsCount: Option[Int] = None,
	  /** Beds count exception. */
		bedsCountException: Option[Schema.LivingAreaSleeping.BedsCountExceptionEnum] = None,
	  /** King beds count. The number of large beds measuring 76"W x 80"L (193cm x 102cm). Most often meant to accompany two people. Includes California king and super king. */
		kingBedsCount: Option[Int] = None,
	  /** King beds count exception. */
		kingBedsCountException: Option[Schema.LivingAreaSleeping.KingBedsCountExceptionEnum] = None,
	  /** Queen beds count. The number of medium-large beds measuring 60"W x 80"L (152cm x 102cm). */
		queenBedsCount: Option[Int] = None,
	  /** Queen beds count exception. */
		queenBedsCountException: Option[Schema.LivingAreaSleeping.QueenBedsCountExceptionEnum] = None,
	  /** Double beds count. The number of medium beds measuring 53"W x 75"L (135cm x 191cm). Also known as full size bed. */
		doubleBedsCount: Option[Int] = None,
	  /** Double beds count exception. */
		doubleBedsCountException: Option[Schema.LivingAreaSleeping.DoubleBedsCountExceptionEnum] = None,
	  /** Single or twin count beds. The number of smaller beds measuring 38"W x 75"L (97cm x 191cm) that can accommodate one adult. */
		singleOrTwinBedsCount: Option[Int] = None,
	  /** Single or twin beds count exception. */
		singleOrTwinBedsCountException: Option[Schema.LivingAreaSleeping.SingleOrTwinBedsCountExceptionEnum] = None,
	  /** Sofa beds count. The number of specially designed sofas that can be made to serve as a bed by lowering its hinged upholstered back to horizontal position or by pulling out a concealed mattress. */
		sofaBedsCount: Option[Int] = None,
	  /** Sofa beds count exception. */
		sofaBedsCountException: Option[Schema.LivingAreaSleeping.SofaBedsCountExceptionEnum] = None,
	  /** Bunk beds count. The number of furniture pieces in which one framed mattress is fixed directly above another by means of a physical frame. This allows one person(s) to sleep in the bottom bunk and one person(s) to sleep in the top bunk. Also known as double decker bed. */
		bunkBedsCount: Option[Int] = None,
	  /** Bunk beds count exception. */
		bunkBedsCountException: Option[Schema.LivingAreaSleeping.BunkBedsCountExceptionEnum] = None,
	  /** Other beds count. The number of beds that are not standard mattress and boxspring setups such as Japanese tatami mats, trundle beds, air mattresses and cots. */
		otherBedsCount: Option[Int] = None,
	  /** Other beds count exception. */
		otherBedsCountException: Option[Schema.LivingAreaSleeping.OtherBedsCountExceptionEnum] = None,
	  /** Roll away beds count. The number of mattresses on wheeled frames that can be folded in half and rolled away for easy storage that the guestroom can obtain upon request. */
		rollAwayBedsCount: Option[Int] = None,
	  /** Roll away beds count exception. */
		rollAwayBedsCountException: Option[Schema.LivingAreaSleeping.RollAwayBedsCountExceptionEnum] = None,
	  /** Cribs count. The number of small beds for an infant or toddler that the guestroom can obtain. The bed is surrounded by a high railing to prevent the child from falling or climbing out of the bed */
		cribsCount: Option[Int] = None,
	  /** Cribs count exception. */
		cribsCountException: Option[Schema.LivingAreaSleeping.CribsCountExceptionEnum] = None,
	  /** Hypoallergenic bedding. Bedding such as linens, pillows, mattress covers and/or mattresses that are made of materials known to be resistant to allergens such as mold, dust and dander. */
		hypoallergenicBedding: Option[Boolean] = None,
	  /** Hypoallergenic bedding exception. */
		hypoallergenicBeddingException: Option[Schema.LivingAreaSleeping.HypoallergenicBeddingExceptionEnum] = None,
	  /** Synthetic pillows. The option for guests to obtain bed pillows stuffed with polyester material crafted to reproduce the feel of a pillow stuffed with down and feathers. */
		syntheticPillows: Option[Boolean] = None,
	  /** Synthetic pillows exception. */
		syntheticPillowsException: Option[Schema.LivingAreaSleeping.SyntheticPillowsExceptionEnum] = None,
	  /** Memory foam pillows. The option for guests to obtain bed pillows that are stuffed with a man-made foam that responds to body heat by conforming to the body closely, and then recovers its shape when the pillow cools down. */
		memoryFoamPillows: Option[Boolean] = None,
	  /** Memory foam pillows exception. */
		memoryFoamPillowsException: Option[Schema.LivingAreaSleeping.MemoryFoamPillowsExceptionEnum] = None,
	  /** Feather pillows. The option for guests to obtain bed pillows that are stuffed with the feathers and down of ducks or geese. */
		featherPillows: Option[Boolean] = None,
	  /** Feather pillows exception. */
		featherPillowsException: Option[Schema.LivingAreaSleeping.FeatherPillowsExceptionEnum] = None
	)
	
	object LivingAreaAccessibility {
		enum MobilityAccessibleUnitExceptionEnum extends Enum[MobilityAccessibleUnitExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum AdaCompliantUnitExceptionEnum extends Enum[AdaCompliantUnitExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum HearingAccessibleUnitExceptionEnum extends Enum[HearingAccessibleUnitExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MobilityAccessibleShowerExceptionEnum extends Enum[MobilityAccessibleShowerExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MobilityAccessibleBathtubExceptionEnum extends Enum[MobilityAccessibleBathtubExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MobilityAccessibleToiletExceptionEnum extends Enum[MobilityAccessibleToiletExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum HearingAccessibleDoorbellExceptionEnum extends Enum[HearingAccessibleDoorbellExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum HearingAccessibleFireAlarmExceptionEnum extends Enum[HearingAccessibleFireAlarmExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class LivingAreaAccessibility(
	  /** Mobility-accessible unit. A guestroom designed to accommodate the physical challenges of a guest with mobility and/or auditory and/or visual issues. Usually features enlarged doorways, roll-in showers with seats, bathroom grab bars, and communication equipment for the hearing and sight challenged. */
		mobilityAccessibleUnit: Option[Boolean] = None,
	  /** Mobility-accessible unit exception. */
		mobilityAccessibleUnitException: Option[Schema.LivingAreaAccessibility.MobilityAccessibleUnitExceptionEnum] = None,
	  /** ADA compliant unit. A guestroom designed to accommodate the physical challenges of a guest with mobility and/or auditory and/or visual issues, as determined by legislative policy. Usually features enlarged doorways, roll-in showers with seats, bathroom grab bars, and communication equipment for the hearing and sight challenged. */
		adaCompliantUnit: Option[Boolean] = None,
	  /** ADA compliant unit exception. */
		adaCompliantUnitException: Option[Schema.LivingAreaAccessibility.AdaCompliantUnitExceptionEnum] = None,
	  /** Hearing-accessible unit. A guestroom designed to accommodate the physical challenges of a guest with auditory issues. */
		hearingAccessibleUnit: Option[Boolean] = None,
	  /** Hearing-accessible unit exception. */
		hearingAccessibleUnitException: Option[Schema.LivingAreaAccessibility.HearingAccessibleUnitExceptionEnum] = None,
	  /** Mobility-accessible shower. A shower with an enlarged door or access point to accommodate a wheelchair or a waterproof seat for the physically challenged. */
		mobilityAccessibleShower: Option[Boolean] = None,
	  /** Mobility-accessible shower exception. */
		mobilityAccessibleShowerException: Option[Schema.LivingAreaAccessibility.MobilityAccessibleShowerExceptionEnum] = None,
	  /** Mobility-accessible bathtub. A bathtub that accomodates the physically challenged with additional railings or hand grips, a transfer seat or lift, and/or a door to enable walking into the tub. */
		mobilityAccessibleBathtub: Option[Boolean] = None,
	  /** Mobility-accessible bathtub exception. */
		mobilityAccessibleBathtubException: Option[Schema.LivingAreaAccessibility.MobilityAccessibleBathtubExceptionEnum] = None,
	  /** Mobility-accessible toilet. A toilet with a higher seat, grab bars, and/or a larger area around it to accommodate the physically challenged. */
		mobilityAccessibleToilet: Option[Boolean] = None,
	  /** Mobility-accessible toilet exception. */
		mobilityAccessibleToiletException: Option[Schema.LivingAreaAccessibility.MobilityAccessibleToiletExceptionEnum] = None,
	  /** Hearing-accessible doorbell. A visual indicator(s) of a knock or ring at the door. */
		hearingAccessibleDoorbell: Option[Boolean] = None,
	  /** Hearing-accessible doorbell exception. */
		hearingAccessibleDoorbellException: Option[Schema.LivingAreaAccessibility.HearingAccessibleDoorbellExceptionEnum] = None,
	  /** Hearing-accessible fire alarm. A device that gives warning of a fire through flashing lights. */
		hearingAccessibleFireAlarm: Option[Boolean] = None,
	  /** Hearing-accessible fire alarm exception. */
		hearingAccessibleFireAlarmException: Option[Schema.LivingAreaAccessibility.HearingAccessibleFireAlarmExceptionEnum] = None
	)
	
	case class GuestUnitType(
	  /** Required. Unit or room code identifiers for a single GuestUnitType. Each code must be unique within a Lodging instance. */
		codes: Option[List[String]] = None,
	  /** Required. Short, English label or name of the GuestUnitType. Target <50 chars. */
		label: Option[String] = None,
	  /** Features and available amenities of the GuestUnitType. */
		features: Option[Schema.GuestUnitFeatures] = None
	)
	
	object GuestUnitFeatures {
		enum TierEnum extends Enum[TierEnum] { case UNIT_TIER_UNSPECIFIED, STANDARD_UNIT, DELUXE_UNIT }
		enum TierExceptionEnum extends Enum[TierExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MaxOccupantsCountExceptionEnum extends Enum[MaxOccupantsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MaxAdultOccupantsCountExceptionEnum extends Enum[MaxAdultOccupantsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum MaxChildOccupantsCountExceptionEnum extends Enum[MaxChildOccupantsCountExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PrivateHomeExceptionEnum extends Enum[PrivateHomeExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum SuiteExceptionEnum extends Enum[SuiteExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum BungalowOrVillaExceptionEnum extends Enum[BungalowOrVillaExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ExecutiveFloorExceptionEnum extends Enum[ExecutiveFloorExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ConnectingUnitAvailableExceptionEnum extends Enum[ConnectingUnitAvailableExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class GuestUnitFeatures(
	  /** Tier. Classification of the unit based on available features/amenities. A non-standard tier is only permitted if at least one other unit type falls under the standard tier. */
		tier: Option[Schema.GuestUnitFeatures.TierEnum] = None,
	  /** Tier exception. */
		tierException: Option[Schema.GuestUnitFeatures.TierExceptionEnum] = None,
	  /** Max occupants count. The total number of guests allowed to stay overnight in the guestroom. */
		maxOccupantsCount: Option[Int] = None,
	  /** Max occupants count exception. */
		maxOccupantsCountException: Option[Schema.GuestUnitFeatures.MaxOccupantsCountExceptionEnum] = None,
	  /** Max adult occupants count. The total number of adult guests allowed to stay overnight in the guestroom. */
		maxAdultOccupantsCount: Option[Int] = None,
	  /** Max adult occupants count exception. */
		maxAdultOccupantsCountException: Option[Schema.GuestUnitFeatures.MaxAdultOccupantsCountExceptionEnum] = None,
	  /** Max child occupants count. The total number of children allowed to stay overnight in the room. */
		maxChildOccupantsCount: Option[Int] = None,
	  /** Max child occupants count exception. */
		maxChildOccupantsCountException: Option[Schema.GuestUnitFeatures.MaxChildOccupantsCountExceptionEnum] = None,
	  /** Private home. A privately owned home (house, townhouse, apartment, cabin, bungalow etc) that may or not serve as the owner's residence, but is rented out in its entirety or by the room(s) to paying guest(s) for vacation stays. Not for lease-based, long-term residency. */
		privateHome: Option[Boolean] = None,
	  /** Private home exception. */
		privateHomeException: Option[Schema.GuestUnitFeatures.PrivateHomeExceptionEnum] = None,
	  /** Suite. A guestroom category that implies both a bedroom area and a separate living area. There may or may not be full walls and doors separating the two areas, but regardless, they are very distinct. Does not mean a couch or chair in a bedroom. */
		suite: Option[Boolean] = None,
	  /** Suite exception. */
		suiteException: Option[Schema.GuestUnitFeatures.SuiteExceptionEnum] = None,
	  /** Bungalow or villa. An independent structure that is part of a hotel or resort that is rented to one party for a vacation stay. The hotel or resort may be completely comprised of bungalows or villas, or they may be one of several guestroom options. Guests in the bungalows or villas most often have the same, if not more, amenities and services offered to guests in other guestroom types. */
		bungalowOrVilla: Option[Boolean] = None,
	  /** Bungalow or villa exception. */
		bungalowOrVillaException: Option[Schema.GuestUnitFeatures.BungalowOrVillaExceptionEnum] = None,
	  /** Executive floor. A floor of the hotel where the guestrooms are only bookable by members of the hotel's frequent guest membership program. Benefits of this room class include access to a designated lounge which may or may not feature free breakfast, cocktails or other perks specific to members of the program. */
		executiveFloor: Option[Boolean] = None,
	  /** Executive floor exception. */
		executiveFloorException: Option[Schema.GuestUnitFeatures.ExecutiveFloorExceptionEnum] = None,
	  /** Connecting unit available. A guestroom type that features access to an adjacent guestroom for the purpose of booking both rooms. Most often used by families who need more than one room to accommodate the number of people in their group. */
		connectingUnitAvailable: Option[Boolean] = None,
	  /** Connecting unit available exception. */
		connectingUnitAvailableException: Option[Schema.GuestUnitFeatures.ConnectingUnitAvailableExceptionEnum] = None,
	  /** Views available from the guest unit itself. */
		views: Option[Schema.ViewsFromUnit] = None,
	  /** Features available in the living areas in the guest unit. */
		totalLivingAreas: Option[Schema.LivingArea] = None
	)
	
	object ViewsFromUnit {
		enum BeachViewExceptionEnum extends Enum[BeachViewExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum CityViewExceptionEnum extends Enum[CityViewExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum GardenViewExceptionEnum extends Enum[GardenViewExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum LakeViewExceptionEnum extends Enum[LakeViewExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum LandmarkViewExceptionEnum extends Enum[LandmarkViewExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum OceanViewExceptionEnum extends Enum[OceanViewExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum PoolViewExceptionEnum extends Enum[PoolViewExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
		enum ValleyViewExceptionEnum extends Enum[ValleyViewExceptionEnum] { case EXCEPTION_UNSPECIFIED, UNDER_CONSTRUCTION, DEPENDENT_ON_SEASON, DEPENDENT_ON_DAY_OF_WEEK }
	}
	case class ViewsFromUnit(
	  /** Beach view. A guestroom that features a window through which guests can see the beach. */
		beachView: Option[Boolean] = None,
	  /** Beach view exception. */
		beachViewException: Option[Schema.ViewsFromUnit.BeachViewExceptionEnum] = None,
	  /** City view. A guestroom that features a window through which guests can see the buildings, parks and/or streets of the city. */
		cityView: Option[Boolean] = None,
	  /** City view exception. */
		cityViewException: Option[Schema.ViewsFromUnit.CityViewExceptionEnum] = None,
	  /** Garden view. A guestroom that features a window through which guests can see a garden. */
		gardenView: Option[Boolean] = None,
	  /** Garden view exception. */
		gardenViewException: Option[Schema.ViewsFromUnit.GardenViewExceptionEnum] = None,
	  /** Lake view. */
		lakeView: Option[Boolean] = None,
	  /** Lake view exception. */
		lakeViewException: Option[Schema.ViewsFromUnit.LakeViewExceptionEnum] = None,
	  /** Landmark view. A guestroom that features a window through which guests can see a landmark such as the countryside, a golf course, the forest, a park, a rain forst, a mountain or a slope. */
		landmarkView: Option[Boolean] = None,
	  /** Landmark view exception. */
		landmarkViewException: Option[Schema.ViewsFromUnit.LandmarkViewExceptionEnum] = None,
	  /** Ocean view. A guestroom that features a window through which guests can see the ocean. */
		oceanView: Option[Boolean] = None,
	  /** Ocean view exception. */
		oceanViewException: Option[Schema.ViewsFromUnit.OceanViewExceptionEnum] = None,
	  /** Pool view. A guestroom that features a window through which guests can see the hotel's swimming pool. */
		poolView: Option[Boolean] = None,
	  /** Pool view exception. */
		poolViewException: Option[Schema.ViewsFromUnit.PoolViewExceptionEnum] = None,
	  /** Valley view. A guestroom that features a window through which guests can see over a valley. */
		valleyView: Option[Boolean] = None,
	  /** Valley view exception. */
		valleyViewException: Option[Schema.ViewsFromUnit.ValleyViewExceptionEnum] = None
	)
	
	case class GetGoogleUpdatedLodgingResponse(
	  /** Required. The Google updated Lodging. */
		lodging: Option[Schema.Lodging] = None,
	  /** Required. The fields in the Lodging that have been updated by Google. Repeated field items are not individually specified. */
		diffMask: Option[String] = None
	)
}
