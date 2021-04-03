<#-- @ftlvariable name="user" type="ru.itmo.wp.domain.User" -->
<#-- @ftlvariable name="pagePost" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="tag" type="ru.itmo.wp.domain.Tag" -->
<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#import "/spring.ftl" as spring/>
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="icon" type="image/png" href="<@spring.url '/favicon.png'/>"/>
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/normalize.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/form.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/datatable.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/article.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/comment.css'/>">
        <script src="<@spring.url '/js/jquery-3.4.1.min.js'/>"></script>
        <script src="<@spring.url '/js/notify.js'/>"></script>
    </head>
    <body>
    <header>
        <a href="/"><img src="<@spring.url '/img/logo.png'/>" alt="Codeforces" title="Codeforces"/></a>
        <div class="languages">
            <a href="#"><img src="<@spring.url '/img/gb.png'/>" alt="In English" title="In English"/></a>
            <a href="#"><img src="<@spring.url '/img/ru.png'/>" alt="In Russian" title="In Russian"/></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                ${user.login}
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <li><a href="/">Home</a></li>
                <li><a href="/users/all">Users</a></li>
                <#if user??>
                    <li><a href="/writePost">Write Post</a></li>
                    <li><a href="/posts">Posts</a></li>
                </#if>
            </ul>
        </nav>
    </header>
    <div class="middle">
        <aside>
            <section>
                <div class="header">
                    Pay attention
                </div>
                <div class="body">
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate ducimus enim facere impedit
                    nobis,
                    nulla placeat quam suscipit unde voluptatibus.
                </div>
                <div class="footer">
                    <a href="#">View all</a>
                </div>
            </section>
        </aside>
        <main>
            <#nested/>
        </main>
    </div>
    <footer>
        <a href="/">Codeforces</a> &copy; 2020 by Mike Mirzayanov
    </footer>
    </body>
    <script>
        <#--noinspection JSUnresolvedFunction-->
        $(function () {
            <#if message??>
            $.notify("${message?js_string}", {className: "success", position: "bottom right"});
            </#if>
        })
    </script>
    </html>
</#macro>

<#macro error field>
    <@spring.bind field/>
    <#if spring.status.errorMessage??>
        <div class="error">${spring.status.errorMessage}</div>
    </#if>
</#macro>

<#macro post pagePost renderComments=false>
<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
    <article>
        <#assign postLink = "/post/${pagePost.id}">
        <a class="titleLink" href=${postLink}>
            <div class="title">${pagePost.title}</div>
        </a>
        <div class="postTags">
            <#if pagePost.tags?size != 0>
                <img src="//sta.codeforces.com/s/13670/images/blog/tags.png">
            </#if>
            <#list pagePost.tags as tag>
                <a href="/tag/${tag.name}" style="text-decoration: none">
                    <span class="postTag">${tag.name}${tag?has_next?string(",","")}</span>
                </a>
            </#list>
        </div>
        <div class="information">By ${pagePost.user.login}, ${pagePost.creationTime}</div>
        <div class="body">${pagePost.text}</div>
        <div class="footer">
            <div class="left">
                <img src="<@spring.url '/img/voteup.png'/>" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="<@spring.url '/img/votedown.png'/>" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="<@spring.url '/img/date_16x16.png'/>" title="Publish Time" alt="Publish Time"/>
                ${pagePost.creationTime}
                <img src="<@spring.url '/img/comments_16x16.png'/>" title="Comments" alt="Comments"/>
                <a href=${postLink}>${pagePost.comments?size}</a>
            </div>
        </div>
    </article>

    <#if user?? && renderComments>
        <div class="form">
            <form method="post" action="">
                <div class="field">
                    <div class="name">
                        <label for="text">Comment Text</label>
                    </div>
                    <div class="value">
                        <input type="hidden" id="userId" name="userId" value="${user.id!}">
                        <input type="hidden" id="postId" name="postId" value="${pagePost.id!}">
                        <textarea class="commentInput" id="text" name="text">${commentForm.text!}</textarea>
                    </div>
                </div>
                <@error "commentForm.text"/>
                <div class="button-field">
                    <input type="submit" value="Write">
                </div>
            </form>
        </div>
    </#if>
    <#if renderComments>
        <#list pagePost.comments as comment>
            <table class="commentMain">
                <tbody>
                <tr>
                    <td class="commentTitle">By ${comment.user.login}:</td>
                    <td class="commentText">${comment.text}</td>
                </tr>
                </tbody>
            </table>
            <div class="commentTime">At ${comment.creationTime}</div>
        </#list>
    </#if>
</#macro>
