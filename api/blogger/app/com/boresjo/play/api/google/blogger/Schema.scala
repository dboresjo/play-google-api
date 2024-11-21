package com.boresjo.play.api.google.blogger

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class PostPerUserInfo(
	  /** ID of the Post resource. */
		postId: Option[String] = None,
	  /** The kind of this entity. Always blogger#postPerUserInfo. */
		kind: Option[String] = None,
	  /** ID of the User. */
		userId: Option[String] = None,
	  /** True if the user has Author level access to the post. */
		hasEditAccess: Option[Boolean] = None,
	  /** ID of the Blog that the post resource belongs to. */
		blogId: Option[String] = None
	)
	
	case class PageList(
	  /** The kind of this entity. Always blogger#pageList. */
		kind: Option[String] = None,
	  /** Pagination token to fetch the next page, if one exists. */
		nextPageToken: Option[String] = None,
	  /** The list of Pages for a Blog. */
		items: Option[List[Schema.Page]] = None,
	  /** Etag of the response. */
		etag: Option[String] = None
	)
	
	case class PostList(
	  /** Pagination token to fetch the previous page, if one exists. */
		prevPageToken: Option[String] = None,
	  /** Etag of the response. */
		etag: Option[String] = None,
	  /** Pagination token to fetch the next page, if one exists. */
		nextPageToken: Option[String] = None,
	  /** The kind of this entity. Always blogger#postList. */
		kind: Option[String] = None,
	  /** The list of Posts for this Blog. */
		items: Option[List[Schema.Post]] = None
	)
	
	case class BlogList(
	  /** Admin level list of blog per-user information. */
		blogUserInfos: Option[List[Schema.BlogUserInfo]] = None,
	  /** The kind of this entity. Always blogger#blogList. */
		kind: Option[String] = None,
	  /** The list of Blogs this user has Authorship or Admin rights over. */
		items: Option[List[Schema.Blog]] = None
	)
	
	object Page {
		case class BlogItem(
		  /** The identifier of the blog containing this page. */
			id: Option[String] = None
		)
		
		object AuthorItem {
			case class ImageItem(
			  /** The creator's avatar URL. */
				url: Option[String] = None
			)
		}
		case class AuthorItem(
		  /** The creator's avatar. */
			image: Option[Schema.Page.AuthorItem.ImageItem] = None,
		  /** The identifier of the creator. */
			id: Option[String] = None,
		  /** The URL of the creator's Profile page. */
			url: Option[String] = None,
		  /** The display name. */
			displayName: Option[String] = None
		)
		
		enum StatusEnum extends Enum[StatusEnum] { case LIVE, DRAFT, SOFT_TRASHED }
	}
	case class Page(
	  /** RFC 3339 date-time when this Page was published. */
		published: Option[String] = None,
	  /** Etag of the resource. */
		etag: Option[String] = None,
	  /** The URL that this Page is displayed at. */
		url: Option[String] = None,
	  /** Data about the blog containing this Page. */
		blog: Option[Schema.Page.BlogItem] = None,
	  /** The author of this Page. */
		author: Option[Schema.Page.AuthorItem] = None,
	  /** The kind of this entity. Always blogger#page. */
		kind: Option[String] = None,
	  /** The status of the page for admin resources (either LIVE or DRAFT). */
		status: Option[Schema.Page.StatusEnum] = None,
	  /** RFC 3339 date-time when this Page was trashed. */
		trashed: Option[String] = None,
	  /** The identifier for this resource. */
		id: Option[String] = None,
	  /** The body content of this Page, in HTML. */
		content: Option[String] = None,
	  /** RFC 3339 date-time when this Page was last updated. */
		updated: Option[String] = None,
	  /** The API REST URL to fetch this resource from. */
		selfLink: Option[String] = None,
	  /** The title of this entity. This is the name displayed in the Admin user interface. */
		title: Option[String] = None
	)
	
	case class PostUserInfo(
	  /** The Post resource. */
		post: Option[Schema.Post] = None,
	  /** Information about a User for the Post. */
		post_user_info: Option[Schema.PostPerUserInfo] = None,
	  /** The kind of this entity. Always blogger#postUserInfo. */
		kind: Option[String] = None
	)
	
	case class PostUserInfosList(
	  /** The list of Posts with User information for the post, for this Blog. */
		items: Option[List[Schema.PostUserInfo]] = None,
	  /** The kind of this entity. Always blogger#postList. */
		kind: Option[String] = None,
	  /** Pagination token to fetch the next page, if one exists. */
		nextPageToken: Option[String] = None
	)
	
	object Pageviews {
		object CountsItem {
			enum TimeRangeEnum extends Enum[TimeRangeEnum] { case ALL_TIME, THIRTY_DAYS, SEVEN_DAYS }
		}
		case class CountsItem(
		  /** Count of page views for the given time range. */
			count: Option[String] = None,
		  /** Time range the given count applies to. */
			timeRange: Option[Schema.Pageviews.CountsItem.TimeRangeEnum] = None
		)
	}
	case class Pageviews(
	  /** The kind of this entry. Always blogger#page_views. */
		kind: Option[String] = None,
	  /** The container of posts in this blog. */
		counts: Option[List[Schema.Pageviews.CountsItem]] = None,
	  /** Blog Id. */
		blogId: Option[String] = None
	)
	
	object BlogPerUserInfo {
		enum RoleEnum extends Enum[RoleEnum] { case VIEW_TYPE_UNSPECIFIED, READER, AUTHOR, ADMIN }
	}
	case class BlogPerUserInfo(
	  /** Access permissions that the user has for the blog (ADMIN, AUTHOR, or READER). */
		role: Option[Schema.BlogPerUserInfo.RoleEnum] = None,
	  /** ID of the User. */
		userId: Option[String] = None,
	  /** The kind of this entity. Always blogger#blogPerUserInfo. */
		kind: Option[String] = None,
	  /** True if the user has Admin level access to the blog. */
		hasAdminAccess: Option[Boolean] = None,
	  /** The Photo Album Key for the user when adding photos to the blog. */
		photosAlbumKey: Option[String] = None,
	  /** ID of the Blog resource. */
		blogId: Option[String] = None
	)
	
	case class CommentList(
	  /** Pagination token to fetch the next page, if one exists. */
		nextPageToken: Option[String] = None,
	  /** The List of Comments for a Post. */
		items: Option[List[Schema.Comment]] = None,
	  /** Etag of the response. */
		etag: Option[String] = None,
	  /** The kind of this entry. Always blogger#commentList. */
		kind: Option[String] = None,
	  /** Pagination token to fetch the previous page, if one exists. */
		prevPageToken: Option[String] = None
	)
	
	object Comment {
		case class InReplyToItem(
		  /** The identified of the parent of this comment. */
			id: Option[String] = None
		)
		
		enum StatusEnum extends Enum[StatusEnum] { case LIVE, EMPTIED, PENDING, SPAM }
		case class BlogItem(
		  /** The identifier of the blog containing this comment. */
			id: Option[String] = None
		)
		
		object AuthorItem {
			case class ImageItem(
			  /** The creator's avatar URL. */
				url: Option[String] = None
			)
		}
		case class AuthorItem(
		  /** The URL of the creator's Profile page. */
			url: Option[String] = None,
		  /** The identifier of the creator. */
			id: Option[String] = None,
		  /** The display name. */
			displayName: Option[String] = None,
		  /** The creator's avatar. */
			image: Option[Schema.Comment.AuthorItem.ImageItem] = None
		)
		
		case class PostItem(
		  /** The identifier of the post containing this comment. */
			id: Option[String] = None
		)
	}
	case class Comment(
	  /** Data about the comment this is in reply to. */
		inReplyTo: Option[Schema.Comment.InReplyToItem] = None,
	  /** The identifier for this resource. */
		id: Option[String] = None,
	  /** The actual content of the comment. May include HTML markup. */
		content: Option[String] = None,
	  /** The status of the comment (only populated for admin users). */
		status: Option[Schema.Comment.StatusEnum] = None,
	  /** The API REST URL to fetch this resource from. */
		selfLink: Option[String] = None,
	  /** Data about the blog containing this comment. */
		blog: Option[Schema.Comment.BlogItem] = None,
	  /** RFC 3339 date-time when this comment was last updated. */
		updated: Option[String] = None,
	  /** RFC 3339 date-time when this comment was published. */
		published: Option[String] = None,
	  /** The author of this Comment. */
		author: Option[Schema.Comment.AuthorItem] = None,
	  /** The kind of this entry. Always blogger#comment. */
		kind: Option[String] = None,
	  /** Data about the post containing this comment. */
		post: Option[Schema.Comment.PostItem] = None
	)
	
	object Blog {
		enum StatusEnum extends Enum[StatusEnum] { case LIVE, DELETED }
		case class PostsItem(
		  /** The count of posts in this blog. */
			totalItems: Option[Int] = None,
		  /** The List of Posts for this Blog. */
			items: Option[List[Schema.Post]] = None,
		  /** The URL of the container for posts in this blog. */
			selfLink: Option[String] = None
		)
		
		case class PagesItem(
		  /** The count of pages in this blog. */
			totalItems: Option[Int] = None,
		  /** The URL of the container for pages in this blog. */
			selfLink: Option[String] = None
		)
		
		case class LocaleItem(
		  /** The language variant this blog is authored in. */
			variant: Option[String] = None,
		  /** The country this blog's locale is set to. */
			country: Option[String] = None,
		  /** The language this blog is authored in. */
			language: Option[String] = None
		)
	}
	case class Blog(
	  /** The status of the blog. */
		status: Option[Schema.Blog.StatusEnum] = None,
	  /** The description of this blog. This is displayed underneath the title. */
		description: Option[String] = None,
	  /** The JSON custom meta-data for the Blog. */
		customMetaData: Option[String] = None,
	  /** The container of posts in this blog. */
		posts: Option[Schema.Blog.PostsItem] = None,
	  /** The API REST URL to fetch this resource from. */
		selfLink: Option[String] = None,
	  /** The kind of this entry. Always blogger#blog. */
		kind: Option[String] = None,
	  /** The name of this blog. This is displayed as the title. */
		name: Option[String] = None,
	  /** RFC 3339 date-time when this blog was published. */
		published: Option[String] = None,
	  /** The container of pages in this blog. */
		pages: Option[Schema.Blog.PagesItem] = None,
	  /** The URL where this blog is published. */
		url: Option[String] = None,
	  /** The locale this Blog is set to. */
		locale: Option[Schema.Blog.LocaleItem] = None,
	  /** The identifier for this resource. */
		id: Option[String] = None,
	  /** RFC 3339 date-time when this blog was last updated. */
		updated: Option[String] = None
	)
	
	object Post {
		object AuthorItem {
			case class ImageItem(
			  /** The creator's avatar URL. */
				url: Option[String] = None
			)
		}
		case class AuthorItem(
		  /** The display name. */
			displayName: Option[String] = None,
		  /** The identifier of the creator. */
			id: Option[String] = None,
		  /** The URL of the creator's Profile page. */
			url: Option[String] = None,
		  /** The creator's avatar. */
			image: Option[Schema.Post.AuthorItem.ImageItem] = None
		)
		
		enum ReaderCommentsEnum extends Enum[ReaderCommentsEnum] { case ALLOW, DONT_ALLOW_SHOW_EXISTING, DONT_ALLOW_HIDE_EXISTING }
		case class LocationItem(
		  /** Location's longitude. */
			lng: Option[BigDecimal] = None,
		  /** Location's latitude. */
			lat: Option[BigDecimal] = None,
		  /** Location name. */
			name: Option[String] = None,
		  /** Location's viewport span. Can be used when rendering a map preview. */
			span: Option[String] = None
		)
		
		case class ImagesItem(
			url: Option[String] = None
		)
		
		enum StatusEnum extends Enum[StatusEnum] { case LIVE, DRAFT, SCHEDULED, SOFT_TRASHED }
		case class BlogItem(
		  /** The identifier of the Blog that contains this Post. */
			id: Option[String] = None
		)
		
		case class RepliesItem(
		  /** The List of Comments for this Post. */
			items: Option[List[Schema.Comment]] = None,
		  /** The count of comments on this post. */
			totalItems: Option[String] = None,
		  /** The URL of the comments on this post. */
			selfLink: Option[String] = None
		)
	}
	case class Post(
	  /** The URL where this Post is displayed. */
		url: Option[String] = None,
	  /** The author of this Post. */
		author: Option[Schema.Post.AuthorItem] = None,
	  /** The title link URL, similar to atom's related link. */
		titleLink: Option[String] = None,
	  /** Comment control and display setting for readers of this post. */
		readerComments: Option[Schema.Post.ReaderCommentsEnum] = None,
	  /** The location for geotagged posts. */
		location: Option[Schema.Post.LocationItem] = None,
	  /** The list of labels this Post was tagged with. */
		labels: Option[List[String]] = None,
	  /** Display image for the Post. */
		images: Option[List[Schema.Post.ImagesItem]] = None,
	  /** The title of the Post. */
		title: Option[String] = None,
	  /** The API REST URL to fetch this resource from. */
		selfLink: Option[String] = None,
	  /** RFC 3339 date-time when this Post was last updated. */
		updated: Option[String] = None,
	  /** Status of the post. Only set for admin-level requests. */
		status: Option[Schema.Post.StatusEnum] = None,
	  /** Etag of the resource. */
		etag: Option[String] = None,
	  /** The content of the Post. May contain HTML markup. */
		content: Option[String] = None,
	  /** RFC 3339 date-time when this Post was last trashed. */
		trashed: Option[String] = None,
	  /** Data about the blog containing this Post. */
		blog: Option[Schema.Post.BlogItem] = None,
	  /** RFC 3339 date-time when this Post was published. */
		published: Option[String] = None,
	  /** The kind of this entity. Always blogger#post. */
		kind: Option[String] = None,
	  /** The identifier of this Post. */
		id: Option[String] = None,
	  /** The JSON meta-data for the Post. */
		customMetaData: Option[String] = None,
	  /** The container of comments on this Post. */
		replies: Option[Schema.Post.RepliesItem] = None
	)
	
	object User {
		case class LocaleItem(
		  /** The country this blog's locale is set to. */
			country: Option[String] = None,
		  /** The language variant this blog is authored in. */
			variant: Option[String] = None,
		  /** The language this blog is authored in. */
			language: Option[String] = None
		)
		
		case class BlogsItem(
		  /** The URL of the Blogs for this user. */
			selfLink: Option[String] = None
		)
	}
	case class User(
	  /** The display name. */
		displayName: Option[String] = None,
	  /** The identifier for this User. */
		id: Option[String] = None,
	  /** The kind of this entity. Always blogger#user. */
		kind: Option[String] = None,
	  /** The timestamp of when this profile was created, in seconds since epoch. */
		created: Option[String] = None,
	  /** This user's locale */
		locale: Option[Schema.User.LocaleItem] = None,
	  /** The container of blogs for this user. */
		blogs: Option[Schema.User.BlogsItem] = None,
	  /** The user's profile page. */
		url: Option[String] = None,
	  /** The API REST URL to fetch this resource from. */
		selfLink: Option[String] = None,
	  /** Profile summary information. */
		about: Option[String] = None
	)
	
	case class BlogUserInfo(
	  /** The Blog resource. */
		blog: Option[Schema.Blog] = None,
	  /** The kind of this entity. Always blogger#blogUserInfo. */
		kind: Option[String] = None,
	  /** Information about a User for the Blog. */
		blog_user_info: Option[Schema.BlogPerUserInfo] = None
	)
}
