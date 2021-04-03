<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if post_id?? && post_id?is_number>
        <#assign p = c.findBy(posts, "id", post_id)!/>
        <#if p?has_content>
            <@c.postMacro p false/>
        <#else>
            No such post
        </#if>
    <#else>
        No such post
    </#if>
</@c.page>