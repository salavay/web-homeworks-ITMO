<#-- @ftlvariable name="uri" type="java.lang.String" -->
<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-vs-coronavirus-65.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <a
                                href="${item.href}"
                                <#if uri?? && uri == item.href>
                                    class="activeMenu"
                                </#if>
                        >
                            ${item.name}
                        </a>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as p>
            <section>
                <div class="header">Post #${p.id}</div>
                <div class="body">
                    <@renderTextWithCut p.text/>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${p.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2020 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly = false>
    <a href="/user?handle=${user.handle}"
            <#if !nameOnly> style="color: ${user.colorOfUsername}; text-decoration: none; font-weight: bold" </#if>
    >
        ${user.handle}
    </a>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findNext items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return items[item?index + 1]/>
        </#if>
    </#list>
</#function>

<#function findPrev items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return items[item?index - 1]/>
        </#if>
    </#list>
</#function>

<#macro postMacro p toCut = true>
    <article>
        <#assign ownerOfPost = findBy(users, "id", p.userId)!/>
        <div class="title">${p.title}</div>
        <div class="information">By
            <@userlink ownerOfPost/>
        </div>
        <div class="body">
            <@renderTextWithCut p.text toCut "/post?post_id=${p.id}"/>
        </div>
    </article>
</#macro>

<#macro renderTextWithCut text toCut = true linkToFull = "">
    <#if toCut && text?length &gt; 250>
        ${text[0..*250]}
        <a <#if linkToFull != "">href="${linkToFull}"</#if>>
            ...
        </a>
    <#else>
        ${text}
    </#if>
</#macro>