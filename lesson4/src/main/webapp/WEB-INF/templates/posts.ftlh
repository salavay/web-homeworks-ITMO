<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id?? && user_id?is_number>
        <#list posts?filter(x -> x.userId == user_id)?reverse as p>
            <@c.postMacro p/>
        </#list>
    <#else>
        No such user
    </#if>
</@c.page>