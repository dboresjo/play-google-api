package com.boresjo.play.api.google.people

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/contacts""" /* See, edit, download, and permanently delete your contacts */,
		"""https://www.googleapis.com/auth/contacts.other.readonly""" /* See and download contact info automatically saved in your "Other contacts" */,
		"""https://www.googleapis.com/auth/contacts.readonly""" /* See and download your contacts */,
		"""https://www.googleapis.com/auth/directory.readonly""" /* See and download your organization's GSuite directory */,
		"""https://www.googleapis.com/auth/user.addresses.read""" /* View your street addresses */,
		"""https://www.googleapis.com/auth/user.birthday.read""" /* See and download your exact date of birth */,
		"""https://www.googleapis.com/auth/user.emails.read""" /* See and download all of your Google Account email addresses */,
		"""https://www.googleapis.com/auth/user.gender.read""" /* See your gender */,
		"""https://www.googleapis.com/auth/user.organization.read""" /* See your education, work history and org info */,
		"""https://www.googleapis.com/auth/user.phonenumbers.read""" /* See and download your personal phone numbers */,
		"""https://www.googleapis.com/auth/userinfo.email""" /* See your primary Google Account email address */,
		"""https://www.googleapis.com/auth/userinfo.profile""" /* See your personal info, including any personal info you've made publicly available */
	)

	private val BASE_URL = "https://people.googleapis.com/"

	object people {
		/** Delete a contact's photo. Mutate requests for the same user should be done sequentially to avoid // lock contention. */
		class deleteContactPhoto(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeleteContactPhotoResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Optional. A field mask to restrict which fields on the person are returned. Multiple fields can be specified by separating them with commas. Defaults to empty if not set, which will skip the post mutate get. Valid values are: &#42; addresses &#42; ageRanges &#42; biographies &#42; birthdays &#42; calendarUrls &#42; clientData &#42; coverPhotos &#42; emailAddresses &#42; events &#42; externalIds &#42; genders &#42; imClients &#42; interests &#42; locales &#42; locations &#42; memberships &#42; metadata &#42; miscKeywords &#42; names &#42; nicknames &#42; occupations &#42; organizations &#42; phoneNumbers &#42; photos &#42; relations &#42; sipAddresses &#42; skills &#42; urls &#42; userDefined<br>Format: google-fieldmask */
			def withPersonFields(personFields: String) = new deleteContactPhoto(req.addQueryStringParameters("personFields" -> personFields.toString))
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new deleteContactPhoto(req.addQueryStringParameters("sources" -> sources.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.DeleteContactPhotoResponse])
		}
		object deleteContactPhoto {
			def apply(peopleId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): deleteContactPhoto = new deleteContactPhoto(ws.url(BASE_URL + s"v1/people/${peopleId}:deleteContactPhoto").addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[deleteContactPhoto, Future[Schema.DeleteContactPhotoResponse]] = (fun: deleteContactPhoto) => fun.apply()
		}
		/** Update a batch of contacts and return a map of resource names to PersonResponses for the updated contacts. Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class batchUpdateContacts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Perform the request */
			def withBatchUpdateContactsRequest(body: Schema.BatchUpdateContactsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateContactsResponse])
		}
		object batchUpdateContacts {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): batchUpdateContacts = new batchUpdateContacts(ws.url(BASE_URL + s"v1/people:batchUpdateContacts").addQueryStringParameters())
		}
		/** Create a batch of new contacts and return the PersonResponses for the newly Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class batchCreateContacts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Perform the request */
			def withBatchCreateContactsRequest(body: Schema.BatchCreateContactsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreateContactsResponse])
		}
		object batchCreateContacts {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): batchCreateContacts = new batchCreateContacts(ws.url(BASE_URL + s"v1/people:batchCreateContacts").addQueryStringParameters())
		}
		/** Create a new contact and return the person resource for that contact. The request returns a 400 error if more than one field is specified on a field that is a singleton for contact sources: &#42; biographies &#42; birthdays &#42; genders &#42; names Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class createContact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new createContact(req.addQueryStringParameters("sources" -> sources.toString))
			/** Perform the request */
			def withPerson(body: Schema.Person) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Person])
		}
		object createContact {
			def apply(personFields: String)(using signer: RequestSigner, ec: ExecutionContext): createContact = new createContact(ws.url(BASE_URL + s"v1/people:createContact").addQueryStringParameters("personFields" -> personFields.toString))
		}
		/** Provides a list of domain profiles and domain contacts in the authenticated user's domain directory that match the search query. */
		class searchDirectoryPeople(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchDirectoryPeopleResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/directory.readonly""")
			/** Optional. Additional data to merge into the directory sources if they are connected through verified join keys such as email addresses or phone numbers.<br>Possible values:<br>DIRECTORY_MERGE_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>DIRECTORY_MERGE_SOURCE_TYPE_CONTACT: User owned contact. */
			def withMergeSources(mergeSources: String) = new searchDirectoryPeople(req.addQueryStringParameters("mergeSources" -> mergeSources.toString))
			/** Optional. The number of people to include in the response. Valid values are between 1 and 500, inclusive. Defaults to 100 if not set or set to 0.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new searchDirectoryPeople(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous response `next_page_token`. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `SearchDirectoryPeople` must match the first call that provided the page token. */
			def withPageToken(pageToken: String) = new searchDirectoryPeople(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchDirectoryPeopleResponse])
		}
		object searchDirectoryPeople {
			def apply(query: String, readMask: String, sources: String)(using signer: RequestSigner, ec: ExecutionContext): searchDirectoryPeople = new searchDirectoryPeople(ws.url(BASE_URL + s"v1/people:searchDirectoryPeople").addQueryStringParameters("query" -> query.toString, "readMask" -> readMask.toString, "sources" -> sources.toString))
			given Conversion[searchDirectoryPeople, Future[Schema.SearchDirectoryPeopleResponse]] = (fun: searchDirectoryPeople) => fun.apply()
		}
		/** Update contact data for an existing contact person. Any non-contact data will not be modified. Any non-contact data in the person to update will be ignored. All fields specified in the `update_mask` will be replaced. The server returns a 400 error if `person.metadata.sources` is not specified for the contact to be updated or if there is no contact source. The server returns a 400 error with reason `"failedPrecondition"` if `person.metadata.sources.etag` is different than the contact's etag, which indicates the contact has changed since its data was read. Clients should get the latest person and merge their updates into the latest person. The server returns a 400 error if `memberships` are being updated and there are no contact group memberships specified on the person. The server returns a 400 error if more than one field is specified on a field that is a singleton for contact sources: &#42; biographies &#42; birthdays &#42; genders &#42; names Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class updateContact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Optional. A field mask to restrict which fields on each person are returned. Multiple fields can be specified by separating them with commas. Defaults to all fields if not set. Valid values are: &#42; addresses &#42; ageRanges &#42; biographies &#42; birthdays &#42; calendarUrls &#42; clientData &#42; coverPhotos &#42; emailAddresses &#42; events &#42; externalIds &#42; genders &#42; imClients &#42; interests &#42; locales &#42; locations &#42; memberships &#42; metadata &#42; miscKeywords &#42; names &#42; nicknames &#42; occupations &#42; organizations &#42; phoneNumbers &#42; photos &#42; relations &#42; sipAddresses &#42; skills &#42; urls &#42; userDefined<br>Format: google-fieldmask */
			def withPersonFields(personFields: String) = new updateContact(req.addQueryStringParameters("personFields" -> personFields.toString))
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new updateContact(req.addQueryStringParameters("sources" -> sources.toString))
			/** Perform the request */
			def withPerson(body: Schema.Person) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Person])
		}
		object updateContact {
			def apply(peopleId :PlayApi, resourceName: String, updatePersonFields: String)(using signer: RequestSigner, ec: ExecutionContext): updateContact = new updateContact(ws.url(BASE_URL + s"v1/people/${peopleId}:updateContact").addQueryStringParameters("resourceName" -> resourceName.toString, "updatePersonFields" -> updatePersonFields.toString))
		}
		/** Delete a contact person. Any non-contact data will not be deleted. Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class deleteContact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object deleteContact {
			def apply(peopleId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): deleteContact = new deleteContact(ws.url(BASE_URL + s"v1/people/${peopleId}:deleteContact").addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[deleteContact, Future[Schema.Empty]] = (fun: deleteContact) => fun.apply()
		}
		/** Update a contact's photo. Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class updateContactPhoto(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Perform the request */
			def withUpdateContactPhotoRequest(body: Schema.UpdateContactPhotoRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.UpdateContactPhotoResponse])
		}
		object updateContactPhoto {
			def apply(peopleId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): updateContactPhoto = new updateContactPhoto(ws.url(BASE_URL + s"v1/people/${peopleId}:updateContactPhoto").addQueryStringParameters("resourceName" -> resourceName.toString))
		}
		/** Provides a list of contacts in the authenticated user's grouped contacts that matches the search query. The query matches on a contact's `names`, `nickNames`, `emailAddresses`, `phoneNumbers`, and `organizations` fields that are from the CONTACT source. &#42;&#42;IMPORTANT&#42;&#42;: Before searching, clients should send a warmup request with an empty query to update the cache. See https://developers.google.com/people/v1/contacts#search_the_users_contacts */
		class searchContacts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""", """https://www.googleapis.com/auth/contacts.readonly""")
			/** Optional. The number of results to return. Defaults to 10 if field is not set, or set to 0. Values greater than 30 will be capped to 30.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new searchContacts(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new searchContacts(req.addQueryStringParameters("sources" -> sources.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchResponse])
		}
		object searchContacts {
			def apply(query: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): searchContacts = new searchContacts(ws.url(BASE_URL + s"v1/people:searchContacts").addQueryStringParameters("query" -> query.toString, "readMask" -> readMask.toString))
			given Conversion[searchContacts, Future[Schema.SearchResponse]] = (fun: searchContacts) => fun.apply()
		}
		/** Provides information about a list of specific people by specifying a list of requested resource names. Use `people/me` to indicate the authenticated user. The request returns a 400 error if 'personFields' is not specified. */
		class getBatchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetPeopleResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""", """https://www.googleapis.com/auth/contacts.other.readonly""", """https://www.googleapis.com/auth/contacts.readonly""", """https://www.googleapis.com/auth/directory.readonly""", """https://www.googleapis.com/auth/user.addresses.read""", """https://www.googleapis.com/auth/user.birthday.read""", """https://www.googleapis.com/auth/user.emails.read""", """https://www.googleapis.com/auth/user.gender.read""", """https://www.googleapis.com/auth/user.organization.read""", """https://www.googleapis.com/auth/user.phonenumbers.read""", """https://www.googleapis.com/auth/userinfo.email""", """https://www.googleapis.com/auth/userinfo.profile""")
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new getBatchGet(req.addQueryStringParameters("sources" -> sources.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetPeopleResponse])
		}
		object getBatchGet {
			def apply(resourceNames: String, requestMaskIncludeField: String, personFields: String)(using signer: RequestSigner, ec: ExecutionContext): getBatchGet = new getBatchGet(ws.url(BASE_URL + s"v1/people:batchGet").addQueryStringParameters("resourceNames" -> resourceNames.toString, "requestMask.includeField" -> requestMaskIncludeField.toString, "personFields" -> personFields.toString))
			given Conversion[getBatchGet, Future[Schema.GetPeopleResponse]] = (fun: getBatchGet) => fun.apply()
		}
		/** Delete a batch of contacts. Any non-contact data will not be deleted. Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class batchDeleteContacts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Perform the request */
			def withBatchDeleteContactsRequest(body: Schema.BatchDeleteContactsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object batchDeleteContacts {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): batchDeleteContacts = new batchDeleteContacts(ws.url(BASE_URL + s"v1/people:batchDeleteContacts").addQueryStringParameters())
		}
		/** Provides information about a person by specifying a resource name. Use `people/me` to indicate the authenticated user. The request returns a 400 error if 'personFields' is not specified. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Person]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""", """https://www.googleapis.com/auth/contacts.other.readonly""", """https://www.googleapis.com/auth/contacts.readonly""", """https://www.googleapis.com/auth/directory.readonly""", """https://www.googleapis.com/auth/user.addresses.read""", """https://www.googleapis.com/auth/user.birthday.read""", """https://www.googleapis.com/auth/user.emails.read""", """https://www.googleapis.com/auth/user.gender.read""", """https://www.googleapis.com/auth/user.organization.read""", """https://www.googleapis.com/auth/user.phonenumbers.read""", """https://www.googleapis.com/auth/userinfo.email""", """https://www.googleapis.com/auth/userinfo.profile""")
			/** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_PROFILE and READ_SOURCE_TYPE_CONTACT if not set.<br>Possible values:<br>READ_SOURCE_TYPE_UNSPECIFIED: Unspecified.<br>READ_SOURCE_TYPE_PROFILE: Returns SourceType.ACCOUNT, SourceType.DOMAIN_PROFILE, and SourceType.PROFILE.<br>READ_SOURCE_TYPE_CONTACT: Returns SourceType.CONTACT.<br>READ_SOURCE_TYPE_DOMAIN_CONTACT: Returns SourceType.DOMAIN_CONTACT.<br>READ_SOURCE_TYPE_OTHER_CONTACT: Returns SourceType.OTHER_CONTACT. */
			def withSources(sources: String) = new get(req.addQueryStringParameters("sources" -> sources.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Person])
		}
		object get {
			def apply(peopleId :PlayApi, resourceName: String, requestMaskIncludeField: String, personFields: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/people/${peopleId}").addQueryStringParameters("resourceName" -> resourceName.toString, "requestMask.includeField" -> requestMaskIncludeField.toString, "personFields" -> personFields.toString))
			given Conversion[get, Future[Schema.Person]] = (fun: get) => fun.apply()
		}
		/** Provides a list of domain profiles and domain contacts in the authenticated user's domain directory. When the `sync_token` is specified, resources deleted since the last sync will be returned as a person with `PersonMetadata.deleted` set to true. When the `page_token` or `sync_token` is specified, all other request parameters must match the first call. Writes may have a propagation delay of several minutes for sync requests. Incremental syncs are not intended for read-after-write use cases. See example usage at [List the directory people that have changed](/people/v1/directory#list_the_directory_people_that_have_changed). */
		class listDirectoryPeople(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDirectoryPeopleResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/directory.readonly""")
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
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDirectoryPeopleResponse])
		}
		object listDirectoryPeople {
			def apply(readMask: String, sources: String)(using signer: RequestSigner, ec: ExecutionContext): listDirectoryPeople = new listDirectoryPeople(ws.url(BASE_URL + s"v1/people:listDirectoryPeople").addQueryStringParameters("readMask" -> readMask.toString, "sources" -> sources.toString))
			given Conversion[listDirectoryPeople, Future[Schema.ListDirectoryPeopleResponse]] = (fun: listDirectoryPeople) => fun.apply()
		}
		object connections {
			/** Provides a list of the authenticated user's contacts. Sync tokens expire 7 days after the full sync. A request with an expired sync token will get an error with an [google.rpc.ErrorInfo](https://cloud.google.com/apis/design/errors#error_info) with reason "EXPIRED_SYNC_TOKEN". In the case of such an error clients should make a full sync request without a `sync_token`. The first page of a full sync request has an additional quota. If the quota is exceeded, a 429 error will be returned. This quota is fixed and can not be increased. When the `sync_token` is specified, resources deleted since the last sync will be returned as a person with `PersonMetadata.deleted` set to true. When the `page_token` or `sync_token` is specified, all other request parameters must match the first call. Writes may have a propagation delay of several minutes for sync requests. Incremental syncs are not intended for read-after-write use cases. See example usage at [List the user's contacts that have changed](/people/v1/contacts#list_the_users_contacts_that_have_changed). */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConnectionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/contacts""", """https://www.googleapis.com/auth/contacts.readonly""")
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
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConnectionsResponse])
			}
			object list {
				def apply(peopleId :PlayApi, resourceName: String, requestMaskIncludeField: String, personFields: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/people/${peopleId}/connections").addQueryStringParameters("resourceName" -> resourceName.toString, "requestMask.includeField" -> requestMaskIncludeField.toString, "personFields" -> personFields.toString))
				given Conversion[list, Future[Schema.ListConnectionsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object otherContacts {
		/** List all "Other contacts", that is contacts that are not in a contact group. "Other contacts" are typically auto created contacts from interactions. Sync tokens expire 7 days after the full sync. A request with an expired sync token will get an error with an [google.rpc.ErrorInfo](https://cloud.google.com/apis/design/errors#error_info) with reason "EXPIRED_SYNC_TOKEN". In the case of such an error clients should make a full sync request without a `sync_token`. The first page of a full sync request has an additional quota. If the quota is exceeded, a 429 error will be returned. This quota is fixed and can not be increased. When the `sync_token` is specified, resources deleted since the last sync will be returned as a person with `PersonMetadata.deleted` set to true. When the `page_token` or `sync_token` is specified, all other request parameters must match the first call. Writes may have a propagation delay of several minutes for sync requests. Incremental syncs are not intended for read-after-write use cases. See example usage at [List the user's other contacts that have changed](/people/v1/other-contacts#list_the_users_other_contacts_that_have_changed). */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOtherContactsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts.other.readonly""")
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
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOtherContactsResponse])
		}
		object list {
			def apply(readMask: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/otherContacts").addQueryStringParameters("readMask" -> readMask.toString))
			given Conversion[list, Future[Schema.ListOtherContactsResponse]] = (fun: list) => fun.apply()
		}
		/** Copies an "Other contact" to a new contact in the user's "myContacts" group Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class copyOtherContactToMyContactsGroup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""", """https://www.googleapis.com/auth/contacts.other.readonly""")
			/** Perform the request */
			def withCopyOtherContactToMyContactsGroupRequest(body: Schema.CopyOtherContactToMyContactsGroupRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Person])
		}
		object copyOtherContactToMyContactsGroup {
			def apply(otherContactsId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): copyOtherContactToMyContactsGroup = new copyOtherContactToMyContactsGroup(ws.url(BASE_URL + s"v1/otherContacts/${otherContactsId}:copyOtherContactToMyContactsGroup").addQueryStringParameters("resourceName" -> resourceName.toString))
		}
		/** Provides a list of contacts in the authenticated user's other contacts that matches the search query. The query matches on a contact's `names`, `emailAddresses`, and `phoneNumbers` fields that are from the OTHER_CONTACT source. &#42;&#42;IMPORTANT&#42;&#42;: Before searching, clients should send a warmup request with an empty query to update the cache. See https://developers.google.com/people/v1/other-contacts#search_the_users_other_contacts */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts.other.readonly""")
			/** Optional. The number of results to return. Defaults to 10 if field is not set, or set to 0. Values greater than 30 will be capped to 30.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchResponse])
		}
		object search {
			def apply(query: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/otherContacts:search").addQueryStringParameters("query" -> query.toString, "readMask" -> readMask.toString))
			given Conversion[search, Future[Schema.SearchResponse]] = (fun: search) => fun.apply()
		}
	}
	object contactGroups {
		/** Create a new contact group owned by the authenticated user. Created contact group names must be unique to the users contact groups. Attempting to create a group with a duplicate name will return a HTTP 409 error. Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Perform the request */
			def withCreateContactGroupRequest(body: Schema.CreateContactGroupRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ContactGroup])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/contactGroups").addQueryStringParameters())
		}
		/** Delete an existing contact group owned by the authenticated user by specifying a contact group resource name. Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Optional. Set to true to also delete the contacts in the specified group. */
			def withDeleteContacts(deleteContacts: Boolean) = new delete(req.addQueryStringParameters("deleteContacts" -> deleteContacts.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(contactGroupsId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/contactGroups/${contactGroupsId}").addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Get a specific contact group owned by the authenticated user by specifying a contact group resource name. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ContactGroup]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""", """https://www.googleapis.com/auth/contacts.readonly""")
			/** Optional. Specifies the maximum number of members to return. Defaults to 0 if not set, which will return zero members.<br>Format: int32 */
			def withMaxMembers(maxMembers: Int) = new get(req.addQueryStringParameters("maxMembers" -> maxMembers.toString))
			/** Optional. A field mask to restrict which fields on the group are returned. Defaults to `metadata`, `groupType`, `memberCount`, and `name` if not set or set to empty. Valid fields are: &#42; clientData &#42; groupType &#42; memberCount &#42; metadata &#42; name<br>Format: google-fieldmask */
			def withGroupFields(groupFields: String) = new get(req.addQueryStringParameters("groupFields" -> groupFields.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ContactGroup])
		}
		object get {
			def apply(contactGroupsId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/contactGroups/${contactGroupsId}").addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[get, Future[Schema.ContactGroup]] = (fun: get) => fun.apply()
		}
		/** Update the name of an existing contact group owned by the authenticated user. Updated contact group names must be unique to the users contact groups. Attempting to create a group with a duplicate name will return a HTTP 409 error. Mutate requests for the same user should be sent sequentially to avoid increased latency and failures. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
			/** Perform the request */
			def withUpdateContactGroupRequest(body: Schema.UpdateContactGroupRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ContactGroup])
		}
		object update {
			def apply(contactGroupsId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/contactGroups/${contactGroupsId}").addQueryStringParameters("resourceName" -> resourceName.toString))
		}
		/** Get a list of contact groups owned by the authenticated user by specifying a list of contact group resource names. */
		class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BatchGetContactGroupsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""", """https://www.googleapis.com/auth/contacts.readonly""")
			/** Optional. Specifies the maximum number of members to return for each group. Defaults to 0 if not set, which will return zero members.<br>Format: int32 */
			def withMaxMembers(maxMembers: Int) = new batchGet(req.addQueryStringParameters("maxMembers" -> maxMembers.toString))
			/** Optional. A field mask to restrict which fields on the group are returned. Defaults to `metadata`, `groupType`, `memberCount`, and `name` if not set or set to empty. Valid fields are: &#42; clientData &#42; groupType &#42; memberCount &#42; metadata &#42; name<br>Format: google-fieldmask */
			def withGroupFields(groupFields: String) = new batchGet(req.addQueryStringParameters("groupFields" -> groupFields.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BatchGetContactGroupsResponse])
		}
		object batchGet {
			def apply(resourceNames: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/contactGroups:batchGet").addQueryStringParameters("resourceNames" -> resourceNames.toString))
			given Conversion[batchGet, Future[Schema.BatchGetContactGroupsResponse]] = (fun: batchGet) => fun.apply()
		}
		/** List all contact groups owned by the authenticated user. Members of the contact groups are not populated. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListContactGroupsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/contacts""", """https://www.googleapis.com/auth/contacts.readonly""")
			/** Optional. The next_page_token value returned from a previous call to [ListContactGroups](/people/api/rest/v1/contactgroups/list). Requests the next page of resources. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The maximum number of resources to return. Valid values are between 1 and 1000, inclusive. Defaults to 30 if not set or set to 0.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A sync token, returned by a previous call to `contactgroups.list`. Only resources changed since the sync token was created will be returned. */
			def withSyncToken(syncToken: String) = new list(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Optional. A field mask to restrict which fields on the group are returned. Defaults to `metadata`, `groupType`, `memberCount`, and `name` if not set or set to empty. Valid fields are: &#42; clientData &#42; groupType &#42; memberCount &#42; metadata &#42; name<br>Format: google-fieldmask */
			def withGroupFields(groupFields: String) = new list(req.addQueryStringParameters("groupFields" -> groupFields.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListContactGroupsResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/contactGroups").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListContactGroupsResponse]] = (fun: list) => fun.apply()
		}
		object members {
			/** Modify the members of a contact group owned by the authenticated user. The only system contact groups that can have members added are `contactGroups/myContacts` and `contactGroups/starred`. Other system contact groups are deprecated and can only have contacts removed. */
			class modify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/contacts""")
				/** Perform the request */
				def withModifyContactGroupMembersRequest(body: Schema.ModifyContactGroupMembersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ModifyContactGroupMembersResponse])
			}
			object modify {
				def apply(contactGroupsId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): modify = new modify(ws.url(BASE_URL + s"v1/contactGroups/${contactGroupsId}/members:modify").addQueryStringParameters("resourceName" -> resourceName.toString))
			}
		}
	}
}
