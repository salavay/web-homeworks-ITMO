<#-- @ftlvariable name="messages" type="ru.itmo.wp.model.domain.talks.Talk[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <form method="post" action="">
        <input type="hidden" name="action" value="send"/>
        <div class="field">
            <div class="name">
                <label for="message">Message</label>
            </div>
            <div class="value">
                <input id="message" name="message" value="${message!}"/>
            </div>
        </div>
        <div class="field">
            <div class="name">
                <label for="targetUserLogin">To User</label>
            </div>
            <div class="value">
                <label>
                    <select name="targetUserLogin">
                        <#list users as user>
                            <option>${user.login}</option>
                        </#list>
                    </select>
                </label>
            </div>
        </div>
        <#if error??>
            <div class="error">
                ${error!}
            </div>
        </#if>
        <div class="button-field">
            <input type="submit" value="SendMessage">
        </div>
    </form>
    <div class="datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Text</th>
                <th>From</th>
                <th>To</th>
            </tr>
            </thead>
            <tbody>
            <#list messages as msg>
                <tr>
                    <td>${msg.id}</td>
                    <td>${msg.text}</td>
                    <td>${msg.sourceUserId}</td>
                    <td>${msg.targetUserId}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
