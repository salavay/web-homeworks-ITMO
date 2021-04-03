<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign u = ""/>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    </#if>
    <#if user_id?? && user_id?is_number>
        <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>
    <#if u?has_content>
        <section class="profile">
            <div class="caption"> <@c.userlink u/> </div>
            <ul>
                <li>id: ${u.id}</li>
                <li>handle: ${u.handle}</li>
                <li>name: ${u.name}</li>
                <li>
                    <a href="/posts?user_id=${u.id}">
                        user's posts: ${posts?filter(x -> x.userId == u.id)?size}
                    </a>
                </li>
            </ul>
            <img class="profileImage" src="../img/no-title.jpg" alt="123">
        </section>
        <nav class="userNav">
            <ul>
                <li>
                    <#assign prevUser = c.findPrev(users, "handle", u.handle)!>
                    <#if prevUser?has_content>
                        <a class="hasNewUser" href="/user?handle=${prevUser.handle}"> ← </a>
                    <#else>
                        <a class="hasNotNewUser"> ← </a>
                    </#if>
                </li>
                <li>
                    <#assign nextUser = c.findNext(users, "handle", u.handle)!>
                    <#if nextUser?has_content>
                        <a class="hasNewUser" href="/user?handle=${nextUser.handle}"> → </a>
                    <#else>
                        <a class="hasNotNewUser"> → </a>
                    </#if>
                </li>
            </ul>
        </nav>
    <#else>
        No such user
    </#if>
</@c.page>
