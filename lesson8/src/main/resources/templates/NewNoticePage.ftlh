<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="notice form-box">
        <div class="header">Enter</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="notice"/>
                <div class="field">
                    <div class="name">
                        <label for="content">Notice text</label>
                    </div>
                    <div class="value">
                        <input id="content" name="content" value="${noticeForm.content!}"/>
                    </div>
                    <@c.error "noticeForm.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Enter">
                </div>
            </form>
        </div>
    </div>
</@c.page>