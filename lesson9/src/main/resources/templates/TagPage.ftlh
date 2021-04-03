<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <#if postsWithTag??>
        <#list postsWithTag as post>
            <@c.post post/>
        </#list>
    <#else>
        No posts with that tag
    </#if>
</@c.page>