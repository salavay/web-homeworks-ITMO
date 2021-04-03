<#-- @ftlvariable name="user" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if pageUser??>
        <div>Login: ${pageUser.login}</div>
        <div>Id: ${pageUser.id}</div>
        <div>Creation Time: ${pageUser.creationTime}</div>
    <#else>
        No such user
    </#if>
</@c.page>