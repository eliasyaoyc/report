<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
    <title>${title}</title>
    <style>
        * {
            padding: 0;
            margin: 0;
            list-style: none;
        }

        body {
            font-family: "Microsoft YaHei", "PingFang SC", "Helvetica", "Arial", "黑体", "宋体", sans-serif;
            line-height: 1.5;
            color: #333;
        }

        .main {
            max-width: 1000px;
            width: 100%;
            background: #fff;
            margin: 0 auto;
            padding-bottom: 40px;
        }

        .main .top {
            width: 100%;
        }

        .main .top img {
            width: 100%;
        }

        .main .top .title {
            text-align: center;
            font-weight: 600;
            color: #2E2E2E;
            letter-spacing: 3px;
            margin-top: 30px;
        }

        .main .top .title span {
            font-size: 12px;
            line-height: 24px;
        }

        .main .top .title p {
            font-size: 50px;
            line-height: 70px;
        }

        .main .top .info {
            width: 422px;
            line-height: 48px;
            border: 1px solid #B3B3B3;
            border-bottom: 0;
            font-size: 12px;
            color: #333;
            margin: 60px auto;
        }

        .main .top .info .item {
            overflow: hidden;
            border-bottom: 1px solid #B3B3B3;
            text-align: center;
        }

        .main .top .info .item .label {
            float: left;
            width: 140px;
            box-sizing: border-box;
            border-right: 1px solid #B3B3B3;
        }

        .main .top .info .item .value {
            float: left;
            width: 280px;
        }

        .main .top .catalogue {
            margin-top: 300px;
        }

        .main .top .catalogue .item {
            width: 80%;
            margin: 40px auto 0 auto;
            text-align: center;
            line-height: 40px;
        }

        .main .top .catalogue .item p {
            font-size: 24px;
            color: #333;
            font-weight: 600;
        }

        .main .top .catalogue .item li {
            font-size: 18px;
            color: #333;
        }

        .box-content {
            margin-top: 120px;
            padding: 24px;
        }

        .box-content .temp-box {
            margin-bottom: 40px;
            overflow: hidden;
        }

        .box-content .temp-box .title {
            font-size: 24px;
            line-height: 26px;
            font-weight: 500;
            color: #333333;
            margin-bottom: 16px;
        }

        .box-content .temp-box .title:before {
            content: "";
            display: inline-block;
            width: 4px;
            height: 18px;
            color: #0074E3;
            background: #0074E3;
            margin-right: 8px;
            vertical-align: middle;
            margin-top: -4px;
        }

        .box-content .temp-box .content {
            padding: 0 12px;
            margin-bottom: 24px;
        }

        .box-content .temp-box .content .small-title {
            font-size: 16px;
            color: #333333;
            line-height: 24px;
            font-weight: 500;
            margin-bottom: 4px;
        }

        .box-content .temp-box .content .desc {
            font-size: 14px;
            color: #657085;
            line-height: 22px;
            margin-bottom: 16px;
        }

        .box-content .temp-box .content .table {
            width: 100%;
            border-collapse: collapse;
            table-layout: auto;
            border: 1px solid #ebeff2;
            font-size: 14px;
        }

        .box-content .temp-box .content .table thead tr {
            border-bottom: 1px solid #ebeff2;
            height: 40px;
        }

        .box-content .temp-box .content .table thead tr th {
            background-color: #f8fafc;
            padding: 0 24px;
            border-right: 1px solid #ebeff2;
            color: #657085;
            font-weight: 400;
            box-sizing: border-box;
        }

        .box-content .temp-box .content .table tbody tr {
            border-bottom: 1px solid #ebeff2;
            height: 40px;
        }

        .box-content .temp-box .content .table tbody tr td {
            padding: 0 24px;
            border-right: 1px solid #ebeff2;
            display: table-cell;
            color: #333;
            box-sizing: border-box;
            text-align: center;
        }

        .box-content .temp-box .content .table-box {
            line-height: 40px;
            font-size: 14px;
            overflow: hidden;
            text-align: center;
            border-top: 1px solid #ebeff2;
            border-left: 1px solid #ebeff2;
        }

        .box-content .temp-box .content .table-box li {
            float: left;
            width: 33.3%;
            box-sizing: border-box;
            border: 1px solid #ebeff2;
            border-top: 0;
            border-left: 0;
        }

        .box-content .temp-box .content .table-box li > p {
            width: 60%;
            float: left;
            box-sizing: border-box;
            border-right: 1px solid #ebeff2;
            background-color: #f8fafc;
            color: #657085;
        }

        .box-content .temp-box .content .table-box li > span {
            width: 40%;
            float: left;
            color: #333;
        }

        .box-content .temp-box .content .total-box {
            width: 100%;
            border: 1px solid #ebeff2;
            padding: 24px 0;
            text-align: center;
            font-size: 12px;
            color: #657085;
        }

        .box-content .temp-box .content .total-box p {
            font-size: 16px;
            font-weight: 500;
            margin-bottom: 4px;
        }

        .box-content .temp-box .content .total-box p span {
            color: #0074E3;
        }

        .echarts-box {
            width: 100%;
            max-height: 300px;
            text-align: center;
        }

        .echarts-box img {
            max-width: 100%;
            max-height: 100%;
        }

        .text {
            font-size: 14px;
            color: #333;
            line-height: 28px;
            margin-top: 10px;
        }
    </style>
</head>

<body>
<div class="main">
    <div class="top">
        <img src="img_background.png"/>
        <div class="title">
            <span>${titleDesc}</span>
            <p>${title}</p>
        </div>
        <div class="info">
            <#list info?keys as key>
                <div class="item">
                    <div class="label">
                        ${key}
                    </div>
                    <div class="value">
                        ${info[key]}
                    </div>
                </div>
            </#list>
        </div>
        <div class="catalogue">
            <img src="img_directory.png"/>
            <#list catalogue?keys as key>
                <div class="item">
                    <p>${key}</p>
                    <ul>
                        <#list catalogue[key] as value>
                            <li>${value}</li>
                        </#list>
                    </ul>
                </div>
            </#list>
        </div>
    </div>
    <div class="box-content">
        <div class="temp-box">
            <#list content?keys as key>
                <div class="title">${key}</div>
                <#assign item = content[key]>
                   <#list item as value>
                    <div class="content">
                        <div class="small-title">${value.index}</div>
                        <#if value.description?? && value.description != "">
                            <div class="desc">${value.description}</div>
                        </#if>
                        <#if value.stat?? && (value.stat?size > 1)>
                            <ul class="table-box">
                                <#list value.stat?keys as key>
                                    <li>
                                        <p>${key}</p><span>${value.stat[key]}</span>
                                    </li>
                                </#list>
                            </ul>
                        </#if>
                        <#if value.stat??&&(value.stat?size = 1)>
                            <ul class="total-box">
                                <#list value.stat?keys as key>
                                    <li>
                                        <p>${key}<span>${value.stat[key]}</span></p>
                                        <#if value.statDescription?? && value.statDescription != "">
                                            ${value.statDescription}
                                        </#if>
                                    </li>
                                </#list>
                            </ul>
                        </#if>
                        <#if value.base64?? && value.base64 != "">
                            <div class="echarts-box">
                                <img src="${value.base64}"/>
                            </div>
                        </#if>
                        <#if value.texts?? && (value.texts?size > 0)>
                            <div class="text">
                                <#list value.texts as t>
                                    <p>${t}</p>
                                </#list>
                            </div>
                        </#if>
                        <#if value.tablesTitle?? && (value.tablesTitle?size > 0)>
                           <table class="table">
                             <tbody>
                             <tr>
                                <#list value.tablesTitle as tt>
                                    <th>${tt}</th>
                                </#list>
                             </tr>
                             </tbody>
                             <tbody>
                              <#list value.tablesValue as t1>
                                <tr>
                                    <#list t1 as tv>
                                        <td>${tv}</td>
                                    </#list>
                                </tr>
                              </#list>
                             </tbody>
                           </table>
                        </#if>
                    </div>
                </#list>
            </#list>
        </div>
    </div>
</div>
</body>

</html>