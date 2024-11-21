package com.boresjo.play.api.google.blogger

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtPostPerUserInfo: Format[Schema.PostPerUserInfo] = Json.format[Schema.PostPerUserInfo]
	given fmtPageList: Format[Schema.PageList] = Json.format[Schema.PageList]
	given fmtPage: Format[Schema.Page] = Json.format[Schema.Page]
	given fmtPostList: Format[Schema.PostList] = Json.format[Schema.PostList]
	given fmtPost: Format[Schema.Post] = Json.format[Schema.Post]
	given fmtBlogList: Format[Schema.BlogList] = Json.format[Schema.BlogList]
	given fmtBlogUserInfo: Format[Schema.BlogUserInfo] = Json.format[Schema.BlogUserInfo]
	given fmtBlog: Format[Schema.Blog] = Json.format[Schema.Blog]
	given fmtPageBlogItem: Format[Schema.Page.BlogItem] = Json.format[Schema.Page.BlogItem]
	given fmtPageAuthorItem: Format[Schema.Page.AuthorItem] = Json.format[Schema.Page.AuthorItem]
	given fmtPageAuthorItemImageItem: Format[Schema.Page.AuthorItem.ImageItem] = Json.format[Schema.Page.AuthorItem.ImageItem]
	given fmtPageStatusEnum: Format[Schema.Page.StatusEnum] = JsonEnumFormat[Schema.Page.StatusEnum]
	given fmtPostUserInfo: Format[Schema.PostUserInfo] = Json.format[Schema.PostUserInfo]
	given fmtPostUserInfosList: Format[Schema.PostUserInfosList] = Json.format[Schema.PostUserInfosList]
	given fmtPageviews: Format[Schema.Pageviews] = Json.format[Schema.Pageviews]
	given fmtPageviewsCountsItem: Format[Schema.Pageviews.CountsItem] = Json.format[Schema.Pageviews.CountsItem]
	given fmtPageviewsCountsItemTimeRangeEnum: Format[Schema.Pageviews.CountsItem.TimeRangeEnum] = JsonEnumFormat[Schema.Pageviews.CountsItem.TimeRangeEnum]
	given fmtBlogPerUserInfo: Format[Schema.BlogPerUserInfo] = Json.format[Schema.BlogPerUserInfo]
	given fmtBlogPerUserInfoRoleEnum: Format[Schema.BlogPerUserInfo.RoleEnum] = JsonEnumFormat[Schema.BlogPerUserInfo.RoleEnum]
	given fmtCommentList: Format[Schema.CommentList] = Json.format[Schema.CommentList]
	given fmtComment: Format[Schema.Comment] = Json.format[Schema.Comment]
	given fmtCommentInReplyToItem: Format[Schema.Comment.InReplyToItem] = Json.format[Schema.Comment.InReplyToItem]
	given fmtCommentStatusEnum: Format[Schema.Comment.StatusEnum] = JsonEnumFormat[Schema.Comment.StatusEnum]
	given fmtCommentBlogItem: Format[Schema.Comment.BlogItem] = Json.format[Schema.Comment.BlogItem]
	given fmtCommentAuthorItem: Format[Schema.Comment.AuthorItem] = Json.format[Schema.Comment.AuthorItem]
	given fmtCommentAuthorItemImageItem: Format[Schema.Comment.AuthorItem.ImageItem] = Json.format[Schema.Comment.AuthorItem.ImageItem]
	given fmtCommentPostItem: Format[Schema.Comment.PostItem] = Json.format[Schema.Comment.PostItem]
	given fmtBlogStatusEnum: Format[Schema.Blog.StatusEnum] = JsonEnumFormat[Schema.Blog.StatusEnum]
	given fmtBlogPostsItem: Format[Schema.Blog.PostsItem] = Json.format[Schema.Blog.PostsItem]
	given fmtBlogPagesItem: Format[Schema.Blog.PagesItem] = Json.format[Schema.Blog.PagesItem]
	given fmtBlogLocaleItem: Format[Schema.Blog.LocaleItem] = Json.format[Schema.Blog.LocaleItem]
	given fmtPostAuthorItem: Format[Schema.Post.AuthorItem] = Json.format[Schema.Post.AuthorItem]
	given fmtPostAuthorItemImageItem: Format[Schema.Post.AuthorItem.ImageItem] = Json.format[Schema.Post.AuthorItem.ImageItem]
	given fmtPostReaderCommentsEnum: Format[Schema.Post.ReaderCommentsEnum] = JsonEnumFormat[Schema.Post.ReaderCommentsEnum]
	given fmtPostLocationItem: Format[Schema.Post.LocationItem] = Json.format[Schema.Post.LocationItem]
	given fmtPostImagesItem: Format[Schema.Post.ImagesItem] = Json.format[Schema.Post.ImagesItem]
	given fmtPostStatusEnum: Format[Schema.Post.StatusEnum] = JsonEnumFormat[Schema.Post.StatusEnum]
	given fmtPostBlogItem: Format[Schema.Post.BlogItem] = Json.format[Schema.Post.BlogItem]
	given fmtPostRepliesItem: Format[Schema.Post.RepliesItem] = Json.format[Schema.Post.RepliesItem]
	given fmtUser: Format[Schema.User] = Json.format[Schema.User]
	given fmtUserLocaleItem: Format[Schema.User.LocaleItem] = Json.format[Schema.User.LocaleItem]
	given fmtUserBlogsItem: Format[Schema.User.BlogsItem] = Json.format[Schema.User.BlogsItem]
}
