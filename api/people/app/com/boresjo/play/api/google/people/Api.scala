package com.boresjo.play.api.google.people

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://people.googleapis.com/"

	object people {
		class deleteContactPhoto(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DeleteContactPhotoResponse]) {
			/** Optional. A field mask to restrict which fields on the person are returned. Multiple fields can be specified by separating them with commas. Defaults to empty if not set, which will skip the post mutate get. Valid values are: &#42; addresses &#42; ageRanges &#42; biographies &#42; birthdays &#42; calendarUrls &#42; clientData &#42; coverPhotos &#42; emailAddresses &#42; events &#42; externalIds &#42; genders &#42; imClients &#42; interests &#42; locales &#42; locations &#42; memberships &#42; metadata &#42; miscKeywords &#42; names &#42; nicknames &#42; occupations &#42; organizations &#42; phoneNumbers &#42; photos &#42; relations &#42; sipAddresses &#42; skills &#42; urls &#42; userDefined<br>Format: google-fieldmask */
			def withPersonFields(personFields: String) = new deleteContactPhoto(req.addQueryStringParameters("personFields" -> personFields.toString))
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new deleteContactPhoto(req.addQueryStringParameters("sources" -> sources.toString))
			def apply() = req.execute("DELETE").map(_.json.as[Schema.DeleteContactPhotoResponse])
		}
		object deleteContactPhoto {
			def apply(peopleId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): deleteContactPhoto = new deleteContactPhoto(auth(ws.url(BASE_URL + s"v1/people/${peopleId}:deleteContactPhoto")).addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[deleteContactPhoto, Future[Schema.DeleteContactPhotoResponse]] = (fun: deleteContactPhoto) => fun.apply()
		}
		class batchUpdateContacts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchUpdateContactsRequest(body: Schema.BatchUpdateContactsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchUpdateContactsResponse])
		}
		object batchUpdateContacts {
			def apply()(using auth: AuthToken, ec: ExecutionContext): batchUpdateContacts = new batchUpdateContacts(auth(ws.url(BASE_URL + s"v1/people:batchUpdateContacts")).addQueryStringParameters())
		}
		class batchCreateContacts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchCreateContactsRequest(body: Schema.BatchCreateContactsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchCreateContactsResponse])
		}
		object batchCreateContacts {
			def apply()(using auth: AuthToken, ec: ExecutionContext): batchCreateContacts = new batchCreateContacts(auth(ws.url(BASE_URL + s"v1/people:batchCreateContacts")).addQueryStringParameters())
		}
		class createContact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new createContact(req.addQueryStringParameters("sources" -> sources.toString))
			def withPerson(body: Schema.Person) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Person])
		}
		object createContact {
			def apply(personFields: String)(using auth: AuthToken, ec: ExecutionContext): createContact = new createContact(auth(ws.url(BASE_URL + s"v1/people:createContact")).addQueryStringParameters("personFields" -> personFields.toString))
		}
		class searchDirectoryPeople(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchDirectoryPeopleResponse]) {
			/** Optional. Additional data to merge into the directory sources if they are connected through verified join keys such as email addresses or phone numbers.<br>Possible values:<br>DIRECTORY_MERGE_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>DIRECTORY_MERGE_SOURCE_TYPE_CONTACT: User owned contact. */
			def withMergeSources(mergeSources: String) = new searchDirectoryPeople(req.addQueryStringParameters("mergeSources" -> mergeSources.toString))
			/** Optional. The number of people to include in the response. Valid values are between 1 and 500, inclusive. Defaults to 100 if not set or set to 0.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new searchDirectoryPeople(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous response `next_page_token`. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `SearchDirectoryPeople` must match the first call that provided the page token. */
			def withPageToken(pageToken: String) = new searchDirectoryPeople(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.SearchDirectoryPeopleResponse])
		}
		object searchDirectoryPeople {
			def apply(query: String, readMask: String, sources: String)(using auth: AuthToken, ec: ExecutionContext): searchDirectoryPeople = new searchDirectoryPeople(auth(ws.url(BASE_URL + s"v1/people:searchDirectoryPeople")).addQueryStringParameters("query" -> query.toString, "readMask" -> readMask.toString, "sources" -> sources.toString))
			given Conversion[searchDirectoryPeople, Future[Schema.SearchDirectoryPeopleResponse]] = (fun: searchDirectoryPeople) => fun.apply()
		}
		class updateContact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. A field mask to restrict which fields on each person are returned. Multiple fields can be specified by separating them with commas. Defaults to all fields if not set. Valid values are: &#42; addresses &#42; ageRanges &#42; biographies &#42; birthdays &#42; calendarUrls &#42; clientData &#42; coverPhotos &#42; emailAddresses &#42; events &#42; externalIds &#42; genders &#42; imClients &#42; interests &#42; locales &#42; locations &#42; memberships &#42; metadata &#42; miscKeywords &#42; names &#42; nicknames &#42; occupations &#42; organizations &#42; phoneNumbers &#42; photos &#42; relations &#42; sipAddresses &#42; skills &#42; urls &#42; userDefined<br>Format: google-fieldmask */
			def withPersonFields(personFields: String) = new updateContact(req.addQueryStringParameters("personFields" -> personFields.toString))
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new updateContact(req.addQueryStringParameters("sources" -> sources.toString))
			def withPerson(body: Schema.Person) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Person])
		}
		object updateContact {
			def apply(peopleId :PlayApi, resourceName: String, updatePersonFields: String)(using auth: AuthToken, ec: ExecutionContext): updateContact = new updateContact(auth(ws.url(BASE_URL + s"v1/people/${peopleId}:updateContact")).addQueryStringParameters("resourceName" -> resourceName.toString, "updatePersonFields" -> updatePersonFields.toString))
		}
		class deleteContact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
		}
		object deleteContact {
			def apply(peopleId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): deleteContact = new deleteContact(auth(ws.url(BASE_URL + s"v1/people/${peopleId}:deleteContact")).addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[deleteContact, Future[Schema.Empty]] = (fun: deleteContact) => fun.apply()
		}
		class updateContactPhoto(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUpdateContactPhotoRequest(body: Schema.UpdateContactPhotoRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.UpdateContactPhotoResponse])
		}
		object updateContactPhoto {
			def apply(peopleId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): updateContactPhoto = new updateContactPhoto(auth(ws.url(BASE_URL + s"v1/people/${peopleId}:updateContactPhoto")).addQueryStringParameters("resourceName" -> resourceName.toString))
		}
		class searchContacts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchResponse]) {
			/** Optional. The number of results to return. Defaults to 10 if field is not set, or set to 0. Values greater than 30 will be capped to 30.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new searchContacts(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new searchContacts(req.addQueryStringParameters("sources" -> sources.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.SearchResponse])
		}
		object searchContacts {
			def apply(query: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): searchContacts = new searchContacts(auth(ws.url(BASE_URL + s"v1/people:searchContacts")).addQueryStringParameters("query" -> query.toString, "readMask" -> readMask.toString))
			given Conversion[searchContacts, Future[Schema.SearchResponse]] = (fun: searchContacts) => fun.apply()
		}
		class getBatchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetPeopleResponse]) {
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new getBatchGet(req.addQueryStringParameters("sources" -> sources.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.GetPeopleResponse])
		}
		object getBatchGet {
			def apply(resourceNames: String, requestMaskIncludeField: String, personFields: String)(using auth: AuthToken, ec: ExecutionContext): getBatchGet = new getBatchGet(auth(ws.url(BASE_URL + s"v1/people:batchGet")).addQueryStringParameters("resourceNames" -> resourceNames.toString, "requestMask.includeField" -> requestMaskIncludeField.toString, "personFields" -> personFields.toString))
			given Conversion[getBatchGet, Future[Schema.GetPeopleResponse]] = (fun: getBatchGet) => fun.apply()
		}
		class batchDeleteContacts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchDeleteContactsRequest(body: Schema.BatchDeleteContactsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
		}
		object batchDeleteContacts {
			def apply()(using auth: AuthToken, ec: ExecutionContext): batchDeleteContacts = new batchDeleteContacts(auth(ws.url(BASE_URL + s"v1/people:batchDeleteContacts")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Person]) {
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_PROFILE and READ_SOURCE_TYPE_CONTACT if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new get(req.addQueryStringParameters("sources" -> sources.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.Person])
		}
		object get {
			def apply(peopleId :PlayApi, resourceName: String, requestMaskIncludeField: String, personFields: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/people/${peopleId}")).addQueryStringParameters("resourceName" -> resourceName.toString, "requestMask.includeField" -> requestMaskIncludeField.toString, "personFields" -> personFields.toString))
			given Conversion[get, Future[Schema.Person]] = (fun: get) => fun.apply()
		}
		class listDirectoryPeople(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDirectoryPeopleResponse]) {
			/** Optional. Additional data to merge into the directory sources if they are connected through verified join keys such as email addresses or phone numbers.<br>Possible values:<br>DIRECTORY_MERGE_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>DIRECTORY_MERGE_SOURCE_TYPE_CONTACT: User owned contact. */
			def withMergeSources(mergeSources: String) = new listDirectoryPeople(req.addQueryStringParameters("mergeSources" -> mergeSources.toString))
			/** Optional. The number of people to include in the response. Valid values are between 1 and 1000, inclusive. Defaults to 100 if not set or set to 0.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new listDirectoryPeople(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous response `next_page_token`. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `people.listDirectoryPeople` must match the first call that provided the page token. */
			def withPageToken(pageToken: String) = new listDirectoryPeople(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. Whether the response should return `next_sync_token`. It can be used to get incremental changes since the last request by setting it on the request `sync_token`. More details about sync behavior at `people.listDirectoryPeople`. */
			def withRequestSyncToken(requestSyncToken: Boolean) = new listDirectoryPeople(req.addQueryStringParameters("requestSyncToken" -> requestSyncToken.toString))
			/** Optional. A sync token, received from a previous response `next_sync_token` Provide this to retrieve only the resources changed since the last request. When syncing, all other parameters provided to `people.listDirectoryPeople` must match the first call that provided the sync token. More details about sync behavior at `people.listDirectoryPeople`. */
			def withSyncToken(syncToken: String) = new listDirectoryPeople(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.ListDirectoryPeopleResponse])
		}
		object listDirectoryPeople {
			def apply(readMask: String, sources: String)(using auth: AuthToken, ec: ExecutionContext): listDirectoryPeople = new listDirectoryPeople(auth(ws.url(BASE_URL + s"v1/people:listDirectoryPeople")).addQueryStringParameters("readMask" -> readMask.toString, "sources" -> sources.toString))
			given Conversion[listDirectoryPeople, Future[Schema.ListDirectoryPeopleResponse]] = (fun: listDirectoryPeople) => fun.apply()
		}
		object connections {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListConnectionsResponse]) {
				/** Optional. A page token, received from a previous response `next_page_token`. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `people.connections.list` must match the first call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The number of connections to include in the response. Valid values are between 1 and 1000, inclusive. Defaults to 100 if not set or set to 0.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. The order in which the connections should be sorted. Defaults to `LAST_MODIFIED_ASCENDING`.<br>Possible values:<br>LAST_MODIFIED_ASCENDING: Sort people by when they were changed; older entries first.<br>LAST_MODIFIED_DESCENDING: Sort people by when they were changed; newer entries first.<br>FIRST_NAME_ASCENDING: Sort people by first name.<br>LAST_NAME_ASCENDING: Sort people by last name. */
				def withSortOrder(sortOrder: String) = new list(req.addQueryStringParameters("sortOrder" -> sortOrder.toString))
				/** Optional. Whether the response should return `next_sync_token` on the last page of results. It can be used to get incremental changes since the last request by setting it on the request `sync_token`. More details about sync behavior at `people.connections.list`. */
				def withRequestSyncToken(requestSyncToken: Boolean) = new list(req.addQueryStringParameters("requestSyncToken" -> requestSyncToken.toString))
				/** Optional. A sync token, received from a previous response `next_sync_token` Provide this to retrieve only the resources changed since the last request. When syncing, all other parameters provided to `people.connections.list` must match the first call that provided the sync token. More details about sync behavior at `people.connections.list`. */
				def withSyncToken(syncToken: String) = new list(req.addQueryStringParameters("syncToken" -> syncToken.toString))
				/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
				def withSources(sources: String) = new list(req.addQueryStringParameters("sources" -> sources.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListConnectionsResponse])
			}
			object list {
				def apply(peopleId :PlayApi, resourceName: String, requestMaskIncludeField: String, personFields: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/people/${peopleId}/connections")).addQueryStringParameters("resourceName" -> resourceName.toString, "requestMask.includeField" -> requestMaskIncludeField.toString, "personFields" -> personFields.toString))
				given Conversion[list, Future[Schema.ListConnectionsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object otherContacts {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOtherContactsResponse]) {
			/** Optional. A page token, received from a previous response `next_page_token`. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `otherContacts.list` must match the first call that provided the page token. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The number of "Other contacts" to include in the response. Valid values are between 1 and 1000, inclusive. Defaults to 100 if not set or set to 0.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. Whether the response should return `next_sync_token` on the last page of results. It can be used to get incremental changes since the last request by setting it on the request `sync_token`. More details about sync behavior at `otherContacts.list`. */
			def withRequestSyncToken(requestSyncToken: Boolean) = new list(req.addQueryStringParameters("requestSyncToken" -> requestSyncToken.toString))
			/** Optional. A sync token, received from a previous response `next_sync_token` Provide this to retrieve only the resources changed since the last request. When syncing, all other parameters provided to `otherContacts.list` must match the first call that provided the sync token. More details about sync behavior at `otherContacts.list`. */
			def withSyncToken(syncToken: String) = new list(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT if not set. Possible values for this field are: &#42; READ_SOURCE_TYPE_CONTACT &#42; READ_SOURCE_TYPE_CONTACT,READ_SOURCE_TYPE_PROFILE Specifying READ_SOURCE_TYPE_PROFILE without specifying READ_SOURCE_TYPE_CONTACT is not permitted.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new list(req.addQueryStringParameters("sources" -> sources.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.ListOtherContactsResponse])
		}
		object list {
			def apply(readMask: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/otherContacts")).addQueryStringParameters("readMask" -> readMask.toString))
			given Conversion[list, Future[Schema.ListOtherContactsResponse]] = (fun: list) => fun.apply()
		}
		class copyOtherContactToMyContactsGroup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCopyOtherContactToMyContactsGroupRequest(body: Schema.CopyOtherContactToMyContactsGroupRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Person])
		}
		object copyOtherContactToMyContactsGroup {
			def apply(otherContactsId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): copyOtherContactToMyContactsGroup = new copyOtherContactToMyContactsGroup(auth(ws.url(BASE_URL + s"v1/otherContacts/${otherContactsId}:copyOtherContactToMyContactsGroup")).addQueryStringParameters("resourceName" -> resourceName.toString))
		}
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchResponse]) {
			/** Optional. The number of results to return. Defaults to 10 if field is not set, or set to 0. Values greater than 30 will be capped to 30.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.SearchResponse])
		}
		object search {
			def apply(query: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1/otherContacts:search")).addQueryStringParameters("query" -> query.toString, "readMask" -> readMask.toString))
			given Conversion[search, Future[Schema.SearchResponse]] = (fun: search) => fun.apply()
		}
	}
	object contactGroups {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCreateContactGroupRequest(body: Schema.CreateContactGroupRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ContactGroup])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/contactGroups")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			/** Optional. Set to true to also delete the contacts in the specified group. */
			def withDeleteContacts(deleteContacts: Boolean) = new delete(req.addQueryStringParameters("deleteContacts" -> deleteContacts.toString))
			def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(contactGroupsId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/contactGroups/${contactGroupsId}")).addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ContactGroup]) {
			/** Optional. Specifies the maximum number of members to return. Defaults to 0 if not set, which will return zero members.<br>Format: int32 */
			def withMaxMembers(maxMembers: Int) = new get(req.addQueryStringParameters("maxMembers" -> maxMembers.toString))
			/** Optional. A field mask to restrict which fields on the group are returned. Defaults to `metadata`, `groupType`, `memberCount`, and `name` if not set or set to empty. Valid fields are: &#42; clientData &#42; groupType &#42; memberCount &#42; metadata &#42; name<br>Format: google-fieldmask */
			def withGroupFields(groupFields: String) = new get(req.addQueryStringParameters("groupFields" -> groupFields.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.ContactGroup])
		}
		object get {
			def apply(contactGroupsId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/contactGroups/${contactGroupsId}")).addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[get, Future[Schema.ContactGroup]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUpdateContactGroupRequest(body: Schema.UpdateContactGroupRequest) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.ContactGroup])
		}
		object update {
			def apply(contactGroupsId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/contactGroups/${contactGroupsId}")).addQueryStringParameters("resourceName" -> resourceName.toString))
		}
		class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BatchGetContactGroupsResponse]) {
			/** Optional. Specifies the maximum number of members to return for each group. Defaults to 0 if not set, which will return zero members.<br>Format: int32 */
			def withMaxMembers(maxMembers: Int) = new batchGet(req.addQueryStringParameters("maxMembers" -> maxMembers.toString))
			/** Optional. A field mask to restrict which fields on the group are returned. Defaults to `metadata`, `groupType`, `memberCount`, and `name` if not set or set to empty. Valid fields are: &#42; clientData &#42; groupType &#42; memberCount &#42; metadata &#42; name<br>Format: google-fieldmask */
			def withGroupFields(groupFields: String) = new batchGet(req.addQueryStringParameters("groupFields" -> groupFields.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.BatchGetContactGroupsResponse])
		}
		object batchGet {
			def apply(resourceNames: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(auth(ws.url(BASE_URL + s"v1/contactGroups:batchGet")).addQueryStringParameters("resourceNames" -> resourceNames.toString))
			given Conversion[batchGet, Future[Schema.BatchGetContactGroupsResponse]] = (fun: batchGet) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListContactGroupsResponse]) {
			/** Optional. The next_page_token value returned from a previous call to [ListContactGroups](/people/api/rest/v1/contactgroups/list). Requests the next page of resources. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The maximum number of resources to return. Valid values are between 1 and 1000, inclusive. Defaults to 30 if not set or set to 0.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A sync token, returned by a previous call to `contactgroups.list`. Only resources changed since the sync token was created will be returned. */
			def withSyncToken(syncToken: String) = new list(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Optional. A field mask to restrict which fields on the group are returned. Defaults to `metadata`, `groupType`, `memberCount`, and `name` if not set or set to empty. Valid fields are: &#42; clientData &#42; groupType &#42; memberCount &#42; metadata &#42; name<br>Format: google-fieldmask */
			def withGroupFields(groupFields: String) = new list(req.addQueryStringParameters("groupFields" -> groupFields.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.ListContactGroupsResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/contactGroups")).addQueryStringParameters())
			given Conversion[list, Future[Schema.ListContactGroupsResponse]] = (fun: list) => fun.apply()
		}
		object members {
			class modify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withModifyContactGroupMembersRequest(body: Schema.ModifyContactGroupMembersRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ModifyContactGroupMembersResponse])
			}
			object modify {
				def apply(contactGroupsId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): modify = new modify(auth(ws.url(BASE_URL + s"v1/contactGroups/${contactGroupsId}/members:modify")).addQueryStringParameters("resourceName" -> resourceName.toString))
			}
		}
	}
}
